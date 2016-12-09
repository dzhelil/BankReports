package com.estafet.pojo.old;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DRamadan on 21-Nov-16.
 */
public class IbanWrapper implements Serializable{

    private List<String> ibans;

    public List<String> getIbans() {
        return ibans;
    }

    public void setIbans(List<String> ibans) {
        this.ibans = ibans;
    }

}
