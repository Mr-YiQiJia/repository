package com.sata.yqj.cqdxer.common.ini;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.exception.InValiDataException;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

public class IniLoader {
    public static Ini readPath(String filePath){
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                FileUtils.touch(file);
            }
            return new Ini(file);
        } catch (IOException e) {
            throw new InValiDataException(I18N.getString("error.ini.load"), e);
        }
    }
}
