package com.iconloop.score.example;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import score.Address;
import score.ObjectReader;
import score.ObjectWriter;

import java.math.BigInteger;
import java.util.Map;

public class Stamp {
  private final BigInteger id;
  private final Address creator;
  private final String image;
  // private final String price; - Always 0.1 ICX
  private Address sender = null;
  private Address receiver = null;
  private String content = null;
  private String title = null;
  private Boolean expired = false;

  public Stamp(BigInteger _id, Address _creator, String _image) {
    // Address _sender, Address _receiver, String _content, String _title
    if (_id == null || _creator == null || _image == null) {
      throw new IllegalArgumentException();
    }
    this.id = _id;
    this.creator = _creator;
    this.image = _image;
    // this.sender = new Address(null);
    // this.receiver = new Address(null);
    // this.content = new String("");
    // this.title = new String("");
  }

  public static void writeObject(ObjectWriter w, Stamp u) {
    w.beginList(8);
    w.write(u.id);
    w.write(u.creator);
    w.write(u.image);
    w.writeNullable(
        u.sender,
        u.receiver,
        u.content,
        u.title);
    w.write(u.expired);
    w.end();
  }

  public static Stamp readObject(ObjectReader r) {
    r.beginList();
    Stamp t = new Stamp(
        r.readBigInteger(), // id
        r.readAddress(), // creator
        r.readString() // image
    );
    t.setSender(r.readNullable(Address.class)); // sender
    t.setReceiver(r.readNullable(Address.class)); // receiver
    t.setContent(r.readNullable(String.class)); // content
    t.setTitle(r.readNullable(String.class)); // title
    t.setExpired(r.readBoolean());
    r.end();
    return t;
  }

  public Address creator() {
    return this.creator;
  }

  public String image() {
    return this.image;
  }

  public BigInteger id() {
    return this.id;
  }

  public Address sender() {
    return this.sender;
  }

  public void setSender(Address _sender) {
    this.sender = _sender;
  }

  public Address receiver() {
    return this.receiver;
  }

  public void setReceiver(Address _receiver) {
    this.receiver = _receiver;
  }

  public String content() {
    return this.content;
  }

  public void setContent(String _content) {
    this.content = _content;
  }

  public String title() {
    return this.title;
  }

  public void setTitle(String _title) {
    this.title = _title;
  }

  public Boolean expired() {
    return this.expired;
  }

  public void setExpired(Boolean _expired) {
    this.expired = _expired;
  }
  // public Object[] getConvertedParams() {
  // if (params == null || params.equals("")) {
  // return null;
  // }
  // JsonValue json = Json.parse(params);
  // if (!json.isArray()) {
  // throw new IllegalArgumentException("Not json array");
  // }
  // JsonArray array = json.asArray();
  // Object[] ret = new Object[array.size()];
  // int i = 0;
  // for (JsonValue item : array) {
  // JsonObject member = item.asObject();
  // if (member.size() != 3) {
  // throw new IllegalArgumentException("Invalid member size");
  // }
  // String name = member.getString("name", null);
  // String type = member.getString("type", null);
  // String value = member.getString("value", null);
  // if (name != null && type != null && value != null) {
  // ret[i++] = convertParam(type, value);
  // } else {
  // throw new IllegalArgumentException("Incomplete params");
  // }
  // }
  // return ret;
  // }

  private Object convertParam(String type, String value) {
    switch (type) {
      case "Address":
        return Address.fromString(value);
      case "str":
        return value;
      case "int":
        if (value.startsWith("0x")) {
          return new BigInteger(value.substring(2), 16);
        }
        return new BigInteger(value);
      case "bool":
        if (value.equals("0x0") || value.equals("false")) {
          return Boolean.FALSE;
        } else if (value.equals("0x1") || value.equals("true")) {
          return Boolean.TRUE;
        }
        break;
      case "bytes":
        if (value.startsWith("0x") && (value.length() % 2 == 0)) {
          String hex = value.substring(2);
          int len = hex.length() / 2;
          byte[] bytes = new byte[len];
          for (int i = 0; i < len; i++) {
            int j = i * 2;
            bytes[i] = (byte) Integer.parseInt(hex.substring(j, j + 2), 16);
          }
          return bytes;
        }
    }
    throw new IllegalArgumentException("Unknown type");
  }

  @Override
  public String toString() {
    return "{" +
        "\"id\":\"" + id + "\"," +
        "\"creator\":\"" + creator + "\"," +
        "\"image\":\"" + image + "\"," +
        "\"sender\":\"" + sender + "\"," +
        "\"receiver\":\"" + receiver + "\"," +
        "\"content\":\"" + content + "\"," +
        "\"title\":\"" + title + "\"," +
        "\"expired\":\"" + expired + "\"" +
        "}";
  }

  // public Map<String, String> toMap() {
  // return Map.of(
  // "_creator", creator.toString(),
  // "_image", getSafeString(image),
  // "_sender", sender.toString(),
  // "_receiver", receiver.toString(),
  // "_content", getSafeString(content),
  // "_title", getSafeString(title),
  // "_expired", (expired) ? "0x1" : "0x0",
  // "_id", id.toString());
  // }

  // private String getSafeString(String s) {
  // if (s == null)
  // return "";
  // return s;
  // }
}
