package com.github.arugal.example.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.ShellBolt;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangwei
 * @date: 2019-05-26/05:39
 */
public class WordCountTopology extends ConfigurableTopology {

    public static void main(String[] args) {
        ConfigurableTopology.start(new WordCountTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new RandomSentenceSpout(), 1);
        builder.setBolt("split", new SplitSentenceJava()).shuffleGrouping("spout");
        builder.setBolt("count", new WordCount()).fieldsGrouping("split", new Fields("word"));


        conf.setDebug(true);
        String topologyName = "word-count";
        conf.setNumWorkers(1);
        if(args != null && args.length > 0) {
            topologyName = args[0];
        }

        return submitLocal(topologyName, conf, builder);
    }

    public static class RandomSentenceSpout extends BaseRichSpout {

        private static final Logger LOG = LoggerFactory.getLogger(RandomSentenceSpout.class);

        private static final long serialVersionUID = -3715536255715511795L;

        private SpoutOutputCollector collector;

        private String[] sentences = new String[]{
                sentence("the cow jumped over the moon"), sentence("an apple a day keeps the doctor away"),
                sentence("four score and seven years ago"), sentence("snow white and the seven dwarfs"), sentence("i am at two with nature")};

        @Override
        public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
            this.collector = spoutOutputCollector;
        }

        @Override
        public void nextTuple() {
            Utils.sleep(100);
            for(String sentence : sentences) {
                LOG.debug("Emitting tuple: {}", sentence);
                collector.emit(new Values(sentence));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("word"));
        }

        protected String sentence(String input) {
            return input;
        }
    }

    public static class TimeStamped extends RandomSentenceSpout {

        private static final long serialVersionUID = -4274230542220690552L;

        private final String prefix;

        private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_SSSSS = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss.SSSSSSSSS");

        public TimeStamped(String prefix) {
            this.prefix = prefix;
        }

        public TimeStamped() {
            prefix = "";
        }

        @Override
        protected String sentence(String input) {
            return String.format("%s%s  %s", prefix, currentDate(), input);
        }

        private String currentDate() {
            return YYYY_MM_DD_HH_MM_SS_SSSSS.format(new Date());
        }
    }


    public static class SplitSentenceJava extends BaseBasicBolt {

        private static final long serialVersionUID = 8682547516761603218L;

        @Override
        public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
            String sentence = tuple.getString(0);
            String[] words = sentence.split(" ");
            for(String word : words) {
                basicOutputCollector.emit(new Values(word));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("word"));
        }
    }


    public static class SplitSentenceMultilang extends ShellBolt implements IRichBolt {

        private static final long serialVersionUID = 313260415273084547L;

        public SplitSentenceMultilang() {
            super("python", "resources/splitsentence.py");
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("word"));
        }

        @Override
        public Map<String, Object> getComponentConfiguration() {
            return null;
        }
    }

    public static class WordCount extends BaseBasicBolt {

        private static final long serialVersionUID = -5011952226725749577L;

        private Map<String, Integer> counts = new HashMap<>();

        @Override
        public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
            String word = tuple.getString(0);
            Integer count = counts.getOrDefault(word, 0);
            counts.put(word, count++);
            basicOutputCollector.emit(new Values(word, count));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("word", "count"));
        }
    }
}
