package com.mzhang.rmbCurrency.jaunt;

import com.jaunt.JauntException;
import com.jaunt.UserAgent;

public class Example1{
  public static void main(String[] args){
    try{
      UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
      userAgent.visit("http://oracle.com"); // visit a url
      System.out.println(userAgent.doc.innerHTML());               //print the content as HTML
    }
    catch(JauntException e){         //if an HTTP/connection error occurs, handle JauntException.
      System.err.println(e);
    }
  }
}