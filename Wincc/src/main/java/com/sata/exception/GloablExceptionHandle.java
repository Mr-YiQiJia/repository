package com.sata.exception;

import com.sata.alter.Alter;
import javafx.application.Platform;
import org.springframework.util.ObjectUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class GloablExceptionHandle {
    private static final Logger log = Logger.getLogger(GloablExceptionHandle.class.toString());

    public void exceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> Platform.runLater(() -> {
                    Throwable root = getRootThrowable(throwable);
                    if (root instanceof InValiDataException) {
                        log.info(root.getMessage());
                        Alter.popup(root.getMessage());
                    } else {
                        StringWriter out = new StringWriter();
                        root.printStackTrace(new PrintWriter(out));
                        log.warning(out.toString());
                    }
                })
        );
    }

    private Throwable getRootThrowable(Throwable abl) {
        Throwable rootCause = abl;
        while (!ObjectUtils.isEmpty(rootCause.getCause())){
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
