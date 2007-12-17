/* Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.persistence;

/**
 * Exception which a database may throw if passed a corrupted UUID string
 * which cannot be used.  Note this exception should <i>not</i> be thrown
 * on load <i>failure</i>, only in the case of unparseable UUIDs.
 *
 * @author Tim Boudreau
 */
public final class InvalidUuidException extends RuntimeException {
    private final String uuid;
    public InvalidUuidException (String uuid) {
        super ("Invalid uuid " + uuid);
        this.uuid = uuid;
    }
    
    public InvalidUuidException (String uuid, Exception cause) {
        super ("Invalid uuid " + uuid, cause);
        this.uuid = uuid;
    }
    
    public InvalidUuidException (String msg, String uuid) {
        super (msg + ": Invalid uuid " + uuid);
        this.uuid = uuid;
    }
    
    public InvalidUuidException (String msg, String uuid, Exception cause) {
        super (msg + ": Invalid uuid " + uuid, cause);
        this.uuid = uuid;
    }
    
    public String getUuidString() {
        return uuid;
    }
}
