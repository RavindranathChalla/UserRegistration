package com.user.main.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class UserLoginUtil {

  /**
   * This method will prepare initial Restricted list
   * 
   * @param restrictedUserNamesList
   */
  public List<String> prepareRestrictList() {
    List<String> restrictList = new ArrayList<String>();
    restrictList.add("cannabis");
    restrictList.add("abuse");
    restrictList.add("crack");
    restrictList.add("damn");
    restrictList.add("drunk");
    restrictList.add("grass");
    return restrictList;
  }

  /***
   * this method will check if user name is existed in the usersList or not. If yes then generate
   * few alternative names to user
   */

  public Map<Boolean, List<String>> generateRandomUserNames(String name, List<String> users,
      Boolean userflag) {

    Boolean flag = Boolean.FALSE;
    Map<Boolean, List<String>> checkUserNames = new HashMap<Boolean, List<String>>();
    List<String> strings = new ArrayList<String>();

    for (int i = 0; i <= 20; i++) {
      String username = userflag == Boolean.TRUE ? "" : name;
      strings.add(name + RandomStringUtils.randomAlphanumeric(7));
    }

    if (users != null && !users.isEmpty()) {
      for (String string : users) {
        if (string.equalsIgnoreCase(name)) {
          flag = Boolean.TRUE;
        }
      }

      List<String> commonlist = new ArrayList<String>(strings);
      if (commonlist.retainAll(users)) {
        strings.removeAll(commonlist);
      }

    } else {
      flag = Boolean.FALSE;
    }
    checkUserNames.put(flag, strings);
    return checkUserNames;

  }

  /****
   * this method will check created user name will contain any restricted word. If yes will return
   * as true
   */
  public Boolean checkRestrictedNames(List<String> restrictedUserNamesList, String userName) {
    Boolean restrictFlag = Boolean.FALSE;
    if (restrictedUserNamesList != null && !restrictedUserNamesList.isEmpty()) {
      for (String restrictedName : restrictedUserNamesList) {
        if (userName.contains(restrictedName)) {
          restrictFlag = Boolean.TRUE;
        }
      }
    }
    return restrictFlag;
  }

  public Boolean userNameValidation(String userName) {
    Boolean validationFlag = Boolean.FALSE;
    if (StringUtils.isBlank(userName) || userName.length() < 6) {
      validationFlag = Boolean.TRUE;
    }
    return validationFlag;
  }

  public Boolean restrictedNameValidation(String userName) {
    Boolean validationFlag = Boolean.FALSE;
    if (StringUtils.isBlank(userName)) {
      validationFlag = Boolean.TRUE;
    }
    return validationFlag;
  }
}
