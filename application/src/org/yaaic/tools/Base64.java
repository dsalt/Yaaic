/*
Yaaic - Yet Another Android IRC Client

Copyright 2011 Darren Salt

This file is part of Yaaic.

Yaaic is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Yaaic is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Yaaic.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.yaaic.tools;

public class Base64
{
    private final static byte EQUALS = (byte)'=';

    private final static byte[] b64 = {
        (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F', (byte)'G', (byte)'H',
        (byte)'I', (byte)'J', (byte)'K', (byte)'L', (byte)'M', (byte)'N', (byte)'O', (byte)'P',
        (byte)'Q', (byte)'R', (byte)'S', (byte)'T', (byte)'U', (byte)'V', (byte)'W', (byte)'X',
        (byte)'Y', (byte)'Z', (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f',
        (byte)'g', (byte)'h', (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m', (byte)'n',
        (byte)'o', (byte)'p', (byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u', (byte)'v',
        (byte)'w', (byte)'x', (byte)'y', (byte)'z', (byte)'0', (byte)'1', (byte)'2', (byte)'3',
        (byte)'4', (byte)'5', (byte)'6', (byte)'7', (byte)'8', (byte)'9', (byte)'+', (byte)'/'
    };

    private Base64() { }

    public static String encodeBytes(byte[] source)
    {

        if( source == null ){
            return "";
        }

        final int len = source.length;
        final int fullwords = len / 3;
        final int remainder = len % 3;
        final int words = fullwords + (remainder > 0 ? 1 : 0);

        byte[] dest = new byte[words * 4];

        int i;
        for (i = 0 ; i < fullwords; ++i ) {
            final int word = (source[i*3] & 0xFF) << 16 | (source[i*3+1] & 0xFF) << 8 | (source[i*3+2] & 0xFF);
            dest[i*4  ] = b64[(word >>> 18)       ];
            dest[i*4+1] = b64[(word >>> 12) & 0x3F];
            dest[i*4+2] = b64[(word >>>  6) & 0x3F];
            dest[i*4+3] = b64[(word       ) & 0x3F];
        }
        switch (remainder) {
            case 1:
                dest[i*4  ] = b64[(source[i*3] >>> 2) & 0x3F];
                dest[i*4+1] = b64[(source[i*3] <<  4) & 0x3F];
                dest[i*4+2] = EQUALS;
                dest[i*4+3] = EQUALS;
                break;
            case 2:
                final int word = (source[i*3] & 0xFF) << 8 | (source[i*3+1] & 0xFF);
                dest[i*4  ] = b64[(word >>> 10)       ];
                dest[i*4+1] = b64[(word >>>  4) & 0x3F];
                dest[i*4+2] = b64[(word <<   2) & 0x3F];
                dest[i*4+3] = EQUALS;
                break;
            default:; // nothing
        }

        return new String(dest);
    }
}
