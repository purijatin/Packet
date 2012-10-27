/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package protocolapi.Exceptions;

import java.net.SocketException;

/**
 *
 * @author Jatin
 */
public class JsocketException extends SocketException
{
    public JsocketException()
    {
        System.err.println("Jsocket Exception is thrown :(");
    }
}
