/*
 * The MIT License (MIT)
 *
 * Copyright © 2016-, Boku Inc., Jimmie Fulton
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.hydramq.core.type;

import io.hydramq.core.net.Command;
import io.netty.buffer.ByteBuf;

/**
 * @author jfulton
 */
public abstract class CommandTypeConverter<T extends Command> extends TypeConverter<T> {

    private final int typeId;

    public CommandTypeConverter(final int typeId) {
        this.typeId = typeId;
    }

    @Override
    public T read(final ConversionContext context, final ByteBuf buffer) {
        int correlationId = buffer.readInt();
        return readObject(context, correlationId, buffer);
    }

    @Override
    public void write(final ConversionContext context, final T instance, final ByteBuf buffer) {
        buffer.writeInt(typeId);
        buffer.writeInt(instance.correlationId());
        writeObject(context, instance, buffer);
    }

    public int typeId() {
        return typeId;
    }

    abstract protected T readObject(ConversionContext context, int correlationId, final ByteBuf buffer);

    abstract protected void writeObject(ConversionContext context, final T instance, final ByteBuf buffer);
}
