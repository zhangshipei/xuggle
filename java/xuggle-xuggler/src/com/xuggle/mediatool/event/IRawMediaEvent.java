/*
 * Copyright (c) 2008, 2009 by Xuggle Incorporated.  All rights reserved.
 * 
 * This file is part of Xuggler.
 * 
 * You can redistribute Xuggler and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Xuggler is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with Xuggler.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xuggle.mediatool.event;

import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaListener;
import com.xuggle.xuggler.IMediaData;

public interface IRawMediaEvent extends IStreamEvent
{

  /**
   * The {@link IMediaData} for this object.
   * May be null if {@link #getJavaData()}
   * is not null.
   * <p>
   * The returned {@link IMediaData} will only be valid for
   * the duration of the callbackand {@link IMediaListener} implementations
   * must not use it after
   * the call returns.  If you need to keep a copy of this data then
   * use {@link IMediaData#copyReference()} to create a reference
   * that will outlive your call.
   * </p>
   * 
   * @return the media data, or null if unavailable
   */
  public abstract IMediaData getMediaData();

  /**
   * The Java object registered with this event.  If null,
   * you must use {@link #getMediaData()}
   * @return the object, or null if not available
   */
  public abstract Object getJavaData();

  /**
   * The time stamp of this media, in {@link TimeUnit#MICROSECONDS}.
   * @return the timeStamp, or null if none.
   */
  public abstract Long getTimeStamp();

  /**
   * Get the time stamp of this media in the specified units.
   * @param unit the time unit
   * @return the time stamp, or null if none
   * @throws IllegalArgumentException if unit is null
   */
  public abstract Long getTimeStamp(TimeUnit unit);

  /**
   * The time unit of {@link #getTimeStamp()}.
   * @return the timeUnit
   */
  public abstract TimeUnit getTimeUnit();

}
