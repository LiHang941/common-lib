package com.github.lihang941;

import java.util.logging.Logger;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/28
 */
public interface Generator {

    Logger LOGGER = Logger.getLogger("Generator");

    void generator() throws Exception;


}
