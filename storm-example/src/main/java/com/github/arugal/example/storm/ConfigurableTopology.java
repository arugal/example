package com.github.arugal.example.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.shade.org.yaml.snakeyaml.Yaml;
import org.apache.storm.shade.org.yaml.snakeyaml.constructor.SafeConstructor;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangwei
 * @date: 2019-05-26/10:20
 */
public abstract class ConfigurableTopology {

    protected Config conf = new Config();

    public static void start(ConfigurableTopology topology, String args[]) {
        String[] remainingArgs = topology.parse(args);
        try {
            topology.run(remainingArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Config loadConf(String resource, Config conf)
            throws FileNotFoundException {
        Yaml yaml = new Yaml(new SafeConstructor());
        Map<String, Object> ret = (Map<String, Object>) yaml.load(new InputStreamReader(
                new FileInputStream(resource), Charset.defaultCharset()));
        if(ret == null) {
            ret = new HashMap<>();
        }
        // If the config consists of a single key 'config', its values are used
        // instead. This means that the same config files can be used with Flux
        // and the ConfigurableTopology.
        else {
            if(ret.size() == 1) {
                Object confNode = ret.get("config");
                if(confNode != null && confNode instanceof Map) {
                    ret = (Map<String, Object>) confNode;
                }
            }
        }
        conf.putAll(ret);
        return conf;
    }

    protected Config getConf() {
        return conf;
    }

    protected abstract int run(String args[]) throws Exception;

    /**
     * Submits the topology with the name taken from the configuration
     **/
    protected int submit(Config conf, TopologyBuilder builder) {
        String name = (String) Utils.get(conf, Config.TOPOLOGY_NAME, null);
        if(StringUtils.isBlank(name)) {
            throw new RuntimeException(
                    "No value found for " + Config.TOPOLOGY_NAME);
        }
        return submit(name, conf, builder);
    }

    /**
     * Submits the topology under a specific name
     **/
    protected int submit(String name, Config conf, TopologyBuilder builder) {
        try {
            StormSubmitter.submitTopology(name, conf,
                    builder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    protected int submitLocal(Config conf, TopologyBuilder builder){
        String name = (String) Utils.get(conf, Config.TOPOLOGY_NAME, null);
        if(StringUtils.isBlank(name)){
            throw new RuntimeException("No value found for "+Config.TOPOLOGY_NAME);
        }
        return submitLocal(name, conf, builder);
    }

    protected int submitLocal(String name, Config conf, TopologyBuilder builder) {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(name, conf, builder.createTopology());
        return 0;
    }

    private String[] parse(String args[]) {

        List<String> newArgs = new ArrayList<>();
        Collections.addAll(newArgs, args);

        Iterator<String> iter = newArgs.iterator();
        while(iter.hasNext()) {
            String param = iter.next();
            if(param.equals("-conf")) {
                if(!iter.hasNext()) {
                    throw new RuntimeException("Conf file not specified");
                }
                iter.remove();
                String resource = iter.next();
                try {
                    loadConf(resource, conf);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File not found : " + resource);
                }
                iter.remove();
            }
        }
        return newArgs.toArray(new String[newArgs.size()]);
    }
}