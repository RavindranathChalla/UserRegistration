package com.user.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.user.main.util.UserLoginUtil;

@Component
@Scope("session")
public class UserLoginBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7598712131031784924L;
  private String userName;
  private List<String> userNamesList;
  private List<String> restrictedUserNamesList;

  private Boolean listEnableFlag = Boolean.FALSE;

  private List<String> displayList;

  /** we are fetching UserAlertList and AttributeList from DB */
  @PostConstruct
  public void init() {
    try {
      /*** This will contain the Restricted User name list */
      UserLoginUtil loginUtil = new UserLoginUtil();
      this.restrictedUserNamesList = loginUtil.prepareRestrictList();
      HttpSession request = createSession();
      request.setAttribute("userRestrictedNames", restrictedUserNamesList);
    } catch (

    Exception e) {
      e.printStackTrace();
    }
  }

  public HttpSession createSession() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    return (HttpSession) facesContext.getExternalContext().getSession(false);
  }

  /*** This method will check if Login user is existed or not */
  public String userLogin() {
    HttpSession request = createSession();
    List<String> usersSession = (List<String>) request.getAttribute("userNames");
    if (usersSession == null || usersSession.isEmpty()) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
          FacesMessage.SEVERITY_INFO, this.userName, ":: Login Failed. Please create user first"));
    } else {
      if (usersSession.contains(this.userName)) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, this.userName, ":: Login Successfully"));
      } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, this.userName,
                ":: Login Failed. Please create user first"));
      }
    }
    this.userName = null;
    return null;
  }

  /****
   * This method will create Restrict new user.
   */
  public String restrictUserCreate() {
    UserLoginUtil loginUtil = new UserLoginUtil();
    Boolean validationFlag = loginUtil.restrictedNameValidation(this.userName);
    if (validationFlag) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
          FacesMessage.SEVERITY_INFO, this.userName, ":: Restricted word Should not be empty"));
    } else {
      HttpSession request = createSession();
      List<String> usersRestrictSession =
          (List<String>) request.getAttribute("userRestrictedNames");
      if (usersRestrictSession == null || usersRestrictSession.isEmpty()) {
        restrictedUserNamesList = new ArrayList<String>();
        restrictedUserNamesList.add(this.userName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, this.userName, ":: Restricted User Created Successfull"));
      } else {
        if (!this.restrictedUserNamesList.contains(userName)) {
          restrictedUserNamesList.add(this.userName);
          FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_INFO, this.userName,
                  "::  Restricted User Created Successfull"));
        } else {
          FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_INFO, this.userName,
                  ":: Restricted User already existed in our List"));
        }
      }
      request.setAttribute("userRestrictedNames", restrictedUserNamesList);
    }
    this.userName = null;
    return null;
  }

  /****
   * This method will create new user. if User is already existed then will give few options to
   * create a user name
   */
  public String userCreate() {
    UserLoginUtil loginUtil = new UserLoginUtil();
    Boolean validationFlag = loginUtil.userNameValidation(this.userName);
    if (validationFlag) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_INFO, this.userName,
              ":: Please enter minimum 6 characters as a user name"));
      listEnableFlag = Boolean.FALSE;
    } else {
      HttpSession request = createSession();
      List<String> usersSession = (List<String>) request.getAttribute("userNames");
      /**
       * passing restricted list and user name so that will get boolean value back. either true or
       * false.
       */
      Boolean restrictFlag =
          loginUtil.checkRestrictedNames(this.restrictedUserNamesList, this.userName);
      /***
       * if true means user name contains restricted word so that we are sending error message to
       * user with list of restricted words
       */
      if (restrictFlag) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, this.userName,
            ":: User Name Contains Restricted word. So please avoid to use below restricted list of words"));
        listEnableFlag = Boolean.TRUE;
        this.displayList = restrictedUserNamesList;
      } else {
        /***
         * First Checking whether userNameList is not empty then will check for create username is
         * there in the userNameList or not
         */
        if (null != userNamesList && !userNamesList.isEmpty()) {
          for (Entry<Boolean, List<String>> entry : loginUtil
              .generateRandomUserNames(this.userName, userNamesList, Boolean.FALSE).entrySet()) {
            /*** if user name is alredy existed then we are providing alternative names to user */
            if (entry.getKey()) {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                  FacesMessage.SEVERITY_INFO, this.userName,
                  ":: User already existed in our List, so please use below alter native user names"));
              listEnableFlag = Boolean.TRUE;
              this.displayList = entry.getValue();
              /**** if user name is not there in the existed list then creating as a new user */
            } else {
              userNamesList.add(this.userName);
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                  FacesMessage.SEVERITY_INFO, this.userName, ":: User Created Successfull"));
              listEnableFlag = Boolean.FALSE;
              request.setAttribute("userNames", userNamesList);
            }
          }
          /**** if userNameList is empty then we are creating user */
        } else {
          userNamesList = new ArrayList<String>();
          userNamesList.add(this.userName);
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
              FacesMessage.SEVERITY_INFO, this.userName, ":: User Created Successfull"));
          listEnableFlag = Boolean.FALSE;
          request.setAttribute("userNames", userNamesList);
        }
      }
    }
    this.userName = null;
    return null;

  }


  /**
   * @return the userNamesList
   */
  public List<String> getUserNamesList() {
    return userNamesList;
  }

  /**
   * @param userNamesList the userNamesList to set
   */
  public void setUserNamesList(List<String> userNamesList) {
    this.userNamesList = userNamesList;
  }

  /**
   * @return the restrictedUserNamesList
   */
  public List<String> getRestrictedUserNamesList() {
    return restrictedUserNamesList;
  }

  /**
   * @param restrictedUserNamesList the restrictedUserNamesList to set
   */
  public void setRestrictedUserNamesList(List<String> restrictedUserNamesList) {
    this.restrictedUserNamesList = restrictedUserNamesList;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }


  /**
   * @return the listEnableFlag
   */
  public Boolean getListEnableFlag() {
    return listEnableFlag;
  }

  /**
   * @param listEnableFlag the listEnableFlag to set
   */
  public void setListEnableFlag(Boolean listEnableFlag) {
    this.listEnableFlag = listEnableFlag;
  }

  /**
   * @return the displayList
   */
  public List<String> getDisplayList() {
    return displayList;
  }

  /**
   * @param displayList the displayList to set
   */
  public void setDisplayList(List<String> displayList) {
    this.displayList = displayList;
  }

}
