package com.vigorous.asynchronized.network.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class FileUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static boolean isFileExist(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * Read file with default charset.
     * 
     * @param file target to read.
     * @return String with file content, null if error occurs.
     */
    public static final String readFile(File file) {
        try {
            return readFile(new FileInputStream(file), DEFAULT_CHARSET);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read InputStream.
     * 
     * @param is
     * @return <b>String</b> String with InputStream content
     * @throws IOException
     */
    public static String readStream(InputStream is) throws IOException {
        InputStreamReader isr = null;
        StringWriter sw = new StringWriter();
        try {
            isr = new InputStreamReader(is, DEFAULT_CHARSET);
            char[] buffer = new char[1024];
            int length = -1;
            while (-1 != (length = isr.read(buffer))) {
                sw.write(buffer, 0, length);
            }
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != isr) {
                isr.close();
            }
            sw.close();
        }
    }

    /**
     * Read file by path.
     * 
     * @param filePath path of target file.
     * @return String with file content, null if error occurs.
     */
    public static final String readFile(String filePath) {
        try {
            return readFile(new FileInputStream(filePath), DEFAULT_CHARSET);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read file by path with certain charset.
     * 
     * @param filePath path of target file.
     * @param charsetName identifies the character converter to use.
     * @return String with file content, null if error occurs.
     */
    public static final String readFile(String filePath, String charsetName) {
        try {
            return readFile(new FileInputStream(filePath), charsetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read file with certain charset.
     * 
     * @param file target to read.
     * @param charsetName identifies the character converter to use.
     * @return String with file content, null if error occurs.
     */
    public static final String readFile(File file, String charsetName) {
        try {
            return readFile(new FileInputStream(file), charsetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Read InputStream with certain charset.
     * 
     * @param is the InputStream from which to read characters.
     * @param charsetName identifies the character converter to use.
     * @return String with InputStream content, null if error occurs.
     */
    public static final String readFile(InputStream is, String charsetName) {
        InputStreamReader isr = null;
        StringWriter sw = new StringWriter();
        try {
            isr = new InputStreamReader(is, charsetName);
            char[] buffer = new char[1024];
            int length = -1;
            while (-1 != (length = isr.read(buffer))) {
                sw.write(buffer, 0, length);
            }
            return sw.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                sw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Save file with default charset.
     * 
     * @param content File content
     * @param file Target file
     * @return true if success.
     */
    public static final boolean saveFile(String content, File file) {
        return saveFile(content, file, DEFAULT_CHARSET);
    }

    /**
     * Save file with default charset.
     * 
     * @param content File content
     * @param filePath path of target file.
     * @return true if success.
     */
    public static final boolean saveFile(String content, String filePath) {
        return saveFile(content, new File(filePath), DEFAULT_CHARSET);
    }

    /**
     * Save file with cetain charset.
     * 
     * @param content File content
     * @param file Target file
     * @param charsetName identifies the character converter to use.
     * @return true if success.
     */
    public static final boolean saveFile(String content, File file,
            String charsetName) {
        try {
            return saveFile(
                    new ByteArrayInputStream(content.getBytes(charsetName)),
                    file, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save file with cetain charset.
     * 
     * @param content File content
     * @param filePath path of target file.
     * @param charsetName identifies the character converter to use.
     * @return true if success.
     */
    public static final boolean saveFile(String content, String filePath,
            String charsetName) {
        return saveFile(content, new File(filePath), charsetName);
    }

    /**
     * Save file with default charset.
     * 
     * @param is File content
     * @param file Target file
     * @return true if success.
     */
    public static final boolean saveFile(InputStream is, File file) {
        return saveFile(is, file, DEFAULT_CHARSET);
    }

    /**
     * Save file with default charset.
     * 
     * @param is File content
     * @param filePath path of target file.
     * @return true if success.
     */
    public static final boolean saveFile(InputStream is, String filePath) {
        return saveFile(is, new File(filePath), DEFAULT_CHARSET);
    }

    /**
     * Save file with certain charset.
     * 
     * @param is File content
     * @param filePath path of target file.
     * @param charsetName identifies the character converter to use.
     * @return true if success.
     */
    public static final boolean saveFile(InputStream is, String filePath,
            String charsetName) {
        return saveFile(is, new File(filePath), charsetName);
    }

    /**
     * Save file with certain charset.
     * 
     * @param is File content
     * @param file Target file
     * @param charsetName identifies the character converter to use.
     * @return true if success.
     */
    public static final boolean saveFile(InputStream is, File file,
            String charsetName) {
        if (!checkFile(file)) {
            return false;
        }

        OutputStreamWriter osw = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, charsetName);
            osw = new OutputStreamWriter(new FileOutputStream(file),
                    charsetName);
            char[] buffer = new char[1024];
            int length = -1;
            while (-1 != (length = isr.read(buffer))) {
                osw.write(buffer, 0, length);
            }
            osw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static boolean copy(String srcPath, String dstPath) {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return false;
        }

        File dstFile = new File(dstPath);
        if (srcFile.isDirectory()) {
            if (dstFile.exists() && !dstFile.isDirectory()) {
                return false;
            }
            File[] files = srcFile.listFiles();
            for (File f : files) {
                if (!copy(f.getAbsolutePath(),
                        dstPath + File.separator + f.getName())) {
                    return false;
                }
            }
            return true;
        } else {
            File parent = dstFile.getParentFile();
            if (null != parent && !parent.exists() && !parent.mkdirs()) {
                return false;
            }
            return copyFile(srcFile, dstFile);
        }
    }

    private static boolean checkFile(File file) {
        File parent = file.getParentFile();
        if (null != parent && !parent.exists() && !parent.mkdirs()) {
            return false;
        }

        if (file.exists() && !file.delete()) {
            return false;
        }

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean copyFile(File src, File dst) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dst);
            byte[] buffer = new byte[1024];
            int byteread = 0;
            while ((byteread = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
