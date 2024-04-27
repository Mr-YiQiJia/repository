package com.sata.yqj.cqdxer.common.view;

import com.sata.yqj.cqdxer.v2.help.HelpStage;
import com.sata.yqj.cqdxer.v2.index.IndexStage;
import com.sata.yqj.cqdxer.v2.stup.StupStage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 窗口统一初始化
 *
 * @author yiqijia
 * @date 2024/4/24 14:27
 */
//@Configuration
public class StageLocalRegister {
    @SneakyThrows
    @Bean
    public IndexStage indexStage() {
        Task<IndexStage> task = new Task<IndexStage>() {
            @Override
            protected IndexStage call() throws Exception {
                return new IndexStage();
            }
        };
        Platform.runLater(task);
        return task.get();
    }

    @SneakyThrows
    @Bean
    public StupStage stupStage() {
        Task<StupStage> task = new Task<StupStage>() {
            @Override
            protected StupStage call() throws Exception {
                return new StupStage();
            }
        };
        Platform.runLater(task);
        return task.get();
    }

    @SneakyThrows
    @Bean
    public HelpStage helpStage() {
        Task<HelpStage> task = new Task<HelpStage>() {
            @Override
            protected HelpStage call() throws Exception {
                return new HelpStage();
            }
        };
        Platform.runLater(task);
        return task.get();
    }
}
