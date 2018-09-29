package com.chenhz.faker;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.chenhz.common.CodeGenerator;
import com.chenhz.common.enums.Application;

public class FakerCodeGenerator extends CodeGenerator {


    private FakerCodeGenerator(Application app) {
        super(app);
    }

    @Override
    public Application getApp() {
        return Application.FAKER;
    }



    public static void main(String[] args) {
        FakerCodeGenerator fakerCodeGenerator = new FakerCodeGenerator(Application.FAKER);
        fakerCodeGenerator.generateByTables("sys_log");
    }

    @Override
    public GlobalConfig getGlobalConfig() {
        return super.getGlobalConfig()
                .setMapperName("%sDao");
    }
}
