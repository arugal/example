package com.github.arugal.curator.example.discovery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: zhangwei
 * @date: 2019-07-14/13:28
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RemoteInstance implements java.io.Serializable {

    private static final long serialVersionUID = 7417948632179814928L;

    private String host;
    private int port;

}
