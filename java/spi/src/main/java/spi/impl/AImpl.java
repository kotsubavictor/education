package spi.impl;

import spi.SPInterface;

/**
 * Created by Viktor on 20.09.2015.
 */
public class AImpl implements SPInterface{
    public String saySomething() {
        return this.getClass().getName();
    }
}
