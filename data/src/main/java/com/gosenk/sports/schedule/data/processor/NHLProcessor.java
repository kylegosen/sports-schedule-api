package com.gosenk.sports.schedule.data.processor;

import org.springframework.stereotype.Service;

@Service
public class NHLProcessor extends MySportsFeedsProcessor implements Processor {

    public NHLProcessor(){
        super("NHL", "nhl", "2018-2019-regular");
    }

    @Override
    public void process() throws Exception {
        super.process();
    }
}
