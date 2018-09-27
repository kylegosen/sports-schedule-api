package com.gosenk.sports.schedule.data.processor;

import org.springframework.stereotype.Service;

@Service
public class MLBProcessor extends MySportsFeedsProcessor implements Processor {

    public MLBProcessor(){
        super("MLB", "mlb", "2018-regular");
    }

    @Override
    public void process() throws Exception {
        super.process();
    }
}
