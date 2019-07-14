/*
 * This file is part of OpenSpaceBox.
 * Copyright (C) 2019 by Yuri Becker <hi@yuri.li>
 *
 * OpenSpaceBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSpaceBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSpaceBox.  If not, see <http://www.gnu.org/licenses/>.
 */

package li.yuri.openspacebox.realmdesigner.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * http://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream/6603018#6603018
 */
public class ByteBufferBackedInputStream extends InputStream {

    private ByteBuffer buffer;

    public ByteBufferBackedInputStream(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        if (!buffer.hasRemaining()) {
            return -1;
        } else {
            return buffer.get() & 0xFF;
        }
    }

    public int read(byte[] bytes, int off, int len) throws IOException {
        if (!buffer.hasRemaining()) {
            return -1;
        } else {
            len = Math.min(len, buffer.remaining());
            buffer.get(bytes, off, len);
            return len;
        }
    }
}
