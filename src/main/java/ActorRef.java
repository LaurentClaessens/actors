/*
Copyright 2016 Laurent Claessens
contact : moky.math@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/

package actors;

/**
 * A reference of an actor that allow to locate it in the actor system.
 * Using this reference it is possible to send a message among actors.
 */

public interface ActorRef<T extends Message> extends Comparable<ActorRef> {

    /**
     * Sends a {@code message} to another actor
     *
     * @param message The message to send
     * @param to The actor to which sending the message
     */

    // An actor that can only *receive* messages of type T 
    // can *send* messages of any type.

    //void send(T message, ActorRef to);
    void send(Message message, ActorRef to);
}
