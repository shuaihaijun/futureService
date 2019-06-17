package com.future.common.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * request请求参数辅助器，用于多次读取请求参数，
 * 备注：如果请求是GET方法，可以直接通过getParameter(String param)方法读取指定参数，可读取多次；
 * 而POST方法的参数是存储在输入流中，只能读一次，不能多次读取。
 * 有时需要在filter里打印请求参数，因而在filter里读取post请求里的输入流后，会导致具体的controller里拿不到请求参数。
 *
 * @author Admin
 * @version: 1.0
 */
public class WrappedHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] bytes;
    private WrappedServletInputStream wrappedServletInputStream;

    /**
     * 通过构造器处理
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public WrappedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);

        //读取输入流里的请求内容，并保存到bytes中
        bytes = copyToByteArray(request.getInputStream());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        this.wrappedServletInputStream = new WrappedServletInputStream(byteArrayInputStream);

        // 把post参数重新写入请求流
        reWriteInputStream();
    }

    /**
     * 把参数重新写进请求里
     */
    public void reWriteInputStream() {
        wrappedServletInputStream.setStream(new ByteArrayInputStream(bytes != null ? bytes : new byte[0]));
    }

    /**
     * 获取流数据
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return wrappedServletInputStream;
    }

    /**
     * 读取输入流里的请求内容并保存到bytes中
     *
     * @param stream 输入流
     * @return byte[]
     * @throws IOException
     */
    public byte[] copyToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //定义一个缓冲区
        byte[] buffer = new byte[4096];
        int read = 0;
        while (read != -1) {
            read = stream.read(buffer);
            if (read > 0) {
                baos.write(buffer, 0, read);
            }
        }
        return baos.toByteArray();
    }

    /**
     * 读取流数据
     *
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(wrappedServletInputStream));
    }

    /**
     * 获取post参数，可以自己再转为相应格式
     */
    public String getRequestParams() throws IOException {
        return new String(bytes, this.getCharacterEncoding());
    }

    /**
     * 定义内部类
     */
    private class WrappedServletInputStream extends ServletInputStream {

        private InputStream stream;

        public WrappedServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        public void setStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
        }
    }
}