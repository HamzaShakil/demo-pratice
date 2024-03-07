package org.prac.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class FileManager {
    static Logger logger = Logger.getLogger(FileManager.class.getName());
        public static Properties getProps(String filename) {
            Properties myProps = new Properties();
            try {
                File propFile = new File("src/main/resources/" + filename + ".properties");
                if (propFile.exists()) {
                    myProps.load(new FileInputStream(propFile));
                } else {
                    logger.info("FileServiceManager Not Found" + filename);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return myProps;
        }
}
