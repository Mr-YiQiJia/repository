package com.sata.view;

import com.sata.common.StrUtlis;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ThinkPad
 * @date 2024/4/27 14:41
 */
@Component
public class StageManager {
    private static final Map<String, StageLoader> stages = new HashMap<>();

    private String getKey(Class<?> clazz) {
        return StrUtlis.toLowerFirst(clazz.getSimpleName());
    }

    public StageLoader put(StageLoader stage) {
        return stages.put(getKey(stage.getClass()), stage);
    }

    public void remove(StageLoader stage) {
        stages.remove(getKey(stage.getClass()));
    }

    public StageLoader get(String name) {
        return stages.get(name);
    }

    public <E> E get(Class<E> clazz) {
        return (E) get(getKey(clazz));
    }
}
