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

import com.iconloop.score.token.irc3.IRC3Basic;
import score.Address;
import score.Context;
import score.DictDB;
import score.VarDB;
import score.annotation.External;

import java.math.BigInteger;

public class IRC3TokenURI extends IRC3Basic {
    private final DictDB<Address, User> users;
    private final VarDB<BigInteger> countUser;

    public IRC3TokenURI(String _name, String _symbol) {
        super(_name, _symbol);
        this.users = Context.newDictDB("users", User.class);
        this.countUser = Context.newVarDB("countUser", BigInteger.class);
        this.countUser.set(BigInteger.ZERO);
    }

    // @External
    // public void mint(BigInteger _tokenId) {
    //     Context.require(Context.getCaller().equals(Context.getOwner()));
    //     super._mint(Context.getCaller(), _tokenId);
    // }

    // @External
    // public void mintURI(String uri) {
    //     Context.require(Context.getCaller().equals(Context.getOwner()));
    //     this.countUser.set(this.countUser.get().add(BigInteger.ONE));
    //     this.users.set(this.countUser.get(), uri);
    //     super._mint(Context.getCaller(), this.countUser.get());
    // }

    @External
    public void registerUser(String _name, String _avatar) {
        Context.require(!isUserAlreadyExist((Context.getCaller())));
        this.countUser.set(this.countUser.get().add(BigInteger.ONE));
        this.users.set(Context.getCaller(), new User(Context.getCaller(), _name, _avatar, this.countUser.get()));
        super._mint(Context.getCaller(), this.countUser.get());
    }

    @External
    public void transfer(Address _to, BigInteger _tokenId) {
        // throw new Error("Unable to transfer Soul Bound Token "+ _tokenId.toString() + " to " + _to.toString());
        throw new UnsupportedOperationException("Unable to transfer Soul Bound Token "+ _tokenId.toString() + " to " + _to.toString());
    }

    // @External
    // public void increaseCount() {
    //     Context.require(Context.getCaller().equals(Context.getOwner()));
    //     this.countUser.set(this.countUser.get().add(BigInteger.ONE));
    // }

    @External(readonly = true)
    public String getUser(Address _address) {
        Context.require(isUserAlreadyExist(_address));
        return this.users.get(_address).toString();
    }

    // @External
    // public void burn(BigInteger _tokenId) {
    //     Address owner = ownerOf(_tokenId);
    //     Context.require(Context.getCaller().equals(owner));
    //     super._burn(_tokenId);
    // }

    @External(readonly = true)
    public BigInteger total() {
        return this.countUser.get();
    }

    private Boolean isUserAlreadyExist(Address _address) {
        return this.users.get(_address) != null;
    }
}