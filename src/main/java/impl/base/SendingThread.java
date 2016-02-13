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

package actors.impl.base;

import actors.Actor;
import actors.Message;

public class SendingThread implements Runnable
{
    private BaseAbsActor actor_to;
    private Message message;

    public SendingThread(Message m, BaseAbsActor t) 
    {
        message=m;
        actor_to=t;
    }
    public void run() 
    {
        actor_to.putInMailBox(message); 
    }
}
