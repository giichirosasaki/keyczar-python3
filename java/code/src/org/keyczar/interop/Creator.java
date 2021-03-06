package org.keyczar.interop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.keyczar.KeyczarTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for json data to call create commands
 */
public class Creator {
  @SuppressWarnings("unused")
  private final String command;
  private final List<List<String>> keyczartCommands;

  public Creator(String command, List<List<String>> keyczartCommands) {
    this.command = command;
    this.keyczartCommands = keyczartCommands;
  }

  public void create() {
    for (List<String> keyczartCommand : keyczartCommands) {
      String [] args = keyczartCommand.toArray(new String[keyczartCommand.size()]);
      KeyczarTool.main(args);
    }
  }

  static Creator read(String jsonString) {
    try {
      JSONObject json = new JSONObject(jsonString);
      return new Creator(
          json.optString("command"),
          buildKeyczartCommands(json.optJSONArray("keyczartCommands")));
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  private static List<List<String>> buildKeyczartCommands(JSONArray jsonArray)
      throws JSONException {
    List<List<String>> list = new ArrayList<List<String>>();
    if (jsonArray != null) {
      int max = jsonArray.length();
      for (int i = 0; i < max; i++) {
        JSONArray innerListJsonArray = jsonArray.optJSONArray(i);
        list.add(toListOfStrings(innerListJsonArray));
      }
    }
    return list;
  }

  private static List<String> toListOfStrings(JSONArray jsonArray) throws JSONException {
    List<String> list = new ArrayList<String>();
    if (jsonArray != null) {
      int max = jsonArray.length();
      for (int i = 0; i < max; i++) {
        list.add(jsonArray.getString(i));
      }
    }
    return list;
  }
}
