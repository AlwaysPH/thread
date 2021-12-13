package com.ph.thread.activeObject;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActiveObject implements Serializable {
    private static final long serialVersionUID = -1449311127720463611L;

    private String name;

    private String address;
}
