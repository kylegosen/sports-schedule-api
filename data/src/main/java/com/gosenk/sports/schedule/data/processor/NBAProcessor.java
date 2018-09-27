package com.gosenk.sports.schedule.data.processor;

import org.springframework.stereotype.Service;

@Service
public class NBAProcessor extends MySportsFeedsProcessor implements Processor {

    public NBAProcessor(){
        super("NBA", "nba", "2018-2019-regular");
    }

    @Override
    public void process() throws Exception {
        super.process();
    }
}
