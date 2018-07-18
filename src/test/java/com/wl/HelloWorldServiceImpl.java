package com.wl;

/**

 */
public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    public void helloWorld(){
        System.out.println("hello world");
    }

    public void setText(String text) {
        this.text = text;
    }


}
