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

public class User {
  private final Address address;
  private final String name;
  private final String avatar;
  private final BigInteger id;

  public User(Address _address, String _name, String _avatar, BigInteger _id) {
    if (_address == null || _id == null) {
      throw new IllegalArgumentException();
    }
    this.address = _address;
    this.name = _name;
    this.avatar = _avatar;
    this.id = _id;
  }

  public static void writeObject(ObjectWriter w, User u) {
    w.beginList(4);
    w.write(u.address);
    w.writeNullable(
        u.name,
        u.avatar);
    w.write(u.id);
    w.end();
  }

  public static User readObject(ObjectReader r) {
    r.beginList();
    User t = new User(
        r.readAddress(),
        r.readNullable(String.class),
        r.readNullable(String.class),
        r.readBigInteger());
    r.end();
    return t;
  }

  public Address address() {
    return this.address;
  }

  public String name() {
    return this.name;
  }

  public String avatar() {
    return this.avatar;
  }

  public BigInteger id() {
    return this.id;
  }

  // public Object[] getConvertedParams() {
  //   if (params == null || params.equals("")) {
  //     return null;
  //   }
  //   JsonValue json = Json.parse(params);
  //   if (!json.isArray()) {
  //     throw new IllegalArgumentException("Not json array");
  //   }
  //   JsonArray array = json.asArray();
  //   Object[] ret = new Object[array.size()];
  //   int i = 0;
  //   for (JsonValue item : array) {
  //     JsonObject member = item.asObject();
  //     if (member.size() != 3) {
  //       throw new IllegalArgumentException("Invalid member size");
  //     }
  //     String name = member.getString("name", null);
  //     String type = member.getString("type", null);
  //     String value = member.getString("value", null);
  //     if (name != null && type != null && value != null) {
  //       ret[i++] = convertParam(type, value);
  //     } else {
  //       throw new IllegalArgumentException("Incomplete params");
  //     }
  //   }
  //   return ret;
  // }

  // private Object convertParam(String type, String value) {
  //   switch (type) {
  //     case "Address":
  //       return Address.fromString(value);
  //     case "str":
  //       return value;
  //     case "int":
  //       if (value.startsWith("0x")) {
  //         return new BigInteger(value.substring(2), 16);
  //       }
  //       return new BigInteger(value);
  //     case "bool":
  //       if (value.equals("0x0") || value.equals("false")) {
  //         return Boolean.FALSE;
  //       } else if (value.equals("0x1") || value.equals("true")) {
  //         return Boolean.TRUE;
  //       }
  //       break;
  //     case "bytes":
  //       if (value.startsWith("0x") && (value.length() % 2 == 0)) {
  //         String hex = value.substring(2);
  //         int len = hex.length() / 2;
  //         byte[] bytes = new byte[len];
  //         for (int i = 0; i < len; i++) {
  //           int j = i * 2;
  //           bytes[i] = (byte) Integer.parseInt(hex.substring(j, j + 2), 16);
  //         }
  //         return bytes;
  //       }
  //   }
  //   throw new IllegalArgumentException("Unknown type");
  // }

  @Override
  public String toString() {
    return "{" +
      "\"address\":\"" + address + "\"," +
      "\"name\":\"" + name + "\"," +
      "\"avatar\":\"" + avatar + "\"," +
      "\"id\":\"" + id + "\"" +
      "}";
  }

  // public Map<String, String> toMap(BigInteger transactionId) {
  //   return Map.of(
  //       "_address", address.toString(),
  //       "_name", getSafeString(name),
  //       "_avatar", getSafeString(avatar),
  //       "_id", id,
  //       "_transactionId", "0x" + transactionId.toString(16));
  // }

  // private String getSafeString(String s) {
  //   if (s == null)
  //     return "";
  //   return s;
  // }
}
