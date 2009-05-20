/*
 * This file is part of Xuggler.
 * 
 * Xuggler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * Xuggler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public
 * License along with Xuggler.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xuggle.utils.event.handler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xuggle.utils.event.Event;
import com.xuggle.utils.event.IEventDispatcher;
import com.xuggle.utils.event.MockNullEventHandler;
import com.xuggle.utils.event.SynchronousEventDispatcher;

public class BoundedHandlerTest
{
  private class TestEvent extends Event
  {
    public TestEvent(Object source) { super(source); }
  }

  @Test
  public final void testBoundedHandler()
  {
    try
    {
      new BoundedHandler<TestEvent>(
          0,
          new MockNullEventHandler<TestEvent>()
          );
      fail("should not get here"); 
    }catch(IllegalArgumentException e){}
    try
    {
      new BoundedHandler<TestEvent>(
          1,
          null
          );
      fail("should not get here"); 
    }catch(IllegalArgumentException e){}
    new BoundedHandler<TestEvent>(
        1,
        new MockNullEventHandler<TestEvent>()
        );
  }

  @Test
  public final void testHandleEvent()
  {
    IEventDispatcher dispatcher = new SynchronousEventDispatcher();
    Object source = new Object();
    TestEvent event = new TestEvent(source);

    int maxCalls = 4;
    BoundedHandler<TestEvent> handler = Handler.getBoundedHandler(
        maxCalls,
        new MockNullEventHandler<TestEvent>()
        );
    dispatcher.addEventHandler(0, TestEvent.class, handler);
    for(int i = 0; i < maxCalls; i++)
    {
      assertEquals(i, handler.getNumTimesExecuted());
      dispatcher.dispatchEvent(event);
      assertEquals(i+1, handler.getNumTimesExecuted());
    }
    // now should no longer execute
    for(int i = 0; i < 5; i++)
    {
      assertEquals(maxCalls, handler.getNumTimesExecuted());
      dispatcher.dispatchEvent(event);
      assertEquals(maxCalls, handler.getNumTimesExecuted());
    }
  }

}