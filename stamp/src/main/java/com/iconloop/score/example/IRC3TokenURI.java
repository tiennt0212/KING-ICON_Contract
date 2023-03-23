/*
 * Copyright 2020 ICONLOOP Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iconloop.score.example;

import score.Address;
import score.Context;
import score.DictDB;
import score.VarDB;
import score.annotation.External;
import com.iconloop.score.token.irc3.IRC3Basic;

import java.math.BigInteger;

public class IRC3TokenURI extends IRC3Basic {
    private final DictDB<BigInteger, Stamp> stamps;
    private final VarDB<BigInteger> countStamp;

    public IRC3TokenURI(String _name, String _symbol) {
        super(_name, _symbol);
        this.stamps = Context.newDictDB("stamps", Stamp.class);
        this.countStamp = Context.newVarDB("countStamp", BigInteger.class);
        this.countStamp.set(BigInteger.ZERO);
    }

    @External
    public void publishStamp(String _image) {
        this.countStamp.set(this.countStamp.get().add(BigInteger.ONE));
        BigInteger nextStampId = this.countStamp.get();
        Stamp newStamp = new Stamp(nextStampId, Context.getCaller(), _image);
        this.stamps.set(nextStampId, newStamp);
    }

    @External
    public void buyStamp(BigInteger _tokenId) {
        super._mint(Context.getCaller(), _tokenId);
    }

    @External
    public void sendMail(BigInteger _tokenId, Address _receiver, String _title, String _content) {
        Stamp selectedStamp = this.stamps.get(_tokenId);
        selectedStamp.setSender(Context.getCaller());
        selectedStamp.setReceiver(_receiver);
        selectedStamp.setContent(_content);
        selectedStamp.setTitle(_title);
        selectedStamp.setExpired(true);
        this.stamps.set(_tokenId, selectedStamp);
        super.transfer(_receiver, _tokenId);
    }

    @External(readonly = true)
    public String getStamp(BigInteger _stampId) {
        // Context.require(isUserAlreadyExist(_tokenId));
        return this.stamps.get(_stampId).toString();
    }

    @External(readonly = true)
    public String getUserStamp(Address _address, int _expired) {
        // Address userAddress = Context.getCaller();
        int total = this.countStamp.get().intValue();
        String entries = "[";
        for (int i = 1; i <= total; i++) {
            BigInteger tokenId = BigInteger.valueOf(i);
            // Get all user stamp
            Stamp stamp = this.stamps.get(tokenId);
            if (_tokenExists(tokenId) && ownerOf(tokenId).equals(_address)) {
                if (((_expired != 0) && stamp.expired()) || !((_expired != 0) || stamp.expired())) {
                    entries += stamp.toString();
                    if (i < total)
                        entries += ",";
                }
            }
        }
        return entries + "]";
    }

    @External(readonly = true)
    public String getSentEmail() {
        Address userAddress = Context.getCaller();
        int total = this.countStamp.get().intValue();
        String entries = "[";
        for (int i = 1; i <= total; i++) {
            BigInteger tokenId = BigInteger.valueOf(i);
            // Get all user stamp
            Stamp stamp = this.stamps.get(tokenId);
            if (_tokenExists(tokenId) && stamp.receiver().equals(userAddress)) {
                entries += stamp.toString();
                if (i < total)
                    entries += ",";
            }
        }
        return entries + "]";
    }

    @External(readonly = true)
    public String getUnmintedStamp() {
        int total = this.countStamp.get().intValue();
        String entries = "[";
        for (int i = 1; i <= total; i++) {
            BigInteger tokenId = BigInteger.valueOf(i);
            Stamp stamp = this.stamps.get(tokenId);
            if (!_tokenExists(tokenId)) {
                // Get all unminted stamp
                entries += stamp.toString();
                if (i < total)
                    entries += ",";
            }
        }
        return entries + "]";
    }

    @External(readonly = true)
    public BigInteger total() {
        return this.countStamp.get();
    }
}