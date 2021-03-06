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

package io.hydramq.core.type.converters;

import io.hydramq.PartitionId;
import io.hydramq.core.net.commands.WriteCursorRequest;
import io.hydramq.core.type.CommandTypeConverter;
import io.hydramq.core.type.ConversionContext;
import io.netty.buffer.ByteBuf;

/**
 * @author jfulton
 */
public class WriteCursorRequestConverter extends CommandTypeConverter<WriteCursorRequest> {

    public WriteCursorRequestConverter() {
        super(702);
    }

    @Override
    protected WriteCursorRequest readObject(ConversionContext context, int correlationId, ByteBuf buffer) {
        return new WriteCursorRequest(correlationId, context.read(PartitionId.class, buffer), context.read(String.class, buffer), buffer.readLong());
    }

    @Override
    protected void writeObject(ConversionContext context, WriteCursorRequest request, ByteBuf buffer) {
        context.write(PartitionId.class, request.getPartitionId(), buffer);
        context.write(String.class, request.getCursorName(), buffer);
        buffer.writeLong(request.getOffset());
    }


}
