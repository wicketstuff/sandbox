/* 
 * The files in this library are DUAL-LICENSED for license compatibility with
 * the db4o (http://db4o.com) object database, as follows:  Db4o is distributed
 * under both commercial licenses and the GNU Public License version 2 (GPLv2).
 * If you obtained your copy of db4o under the GPLv2 license, then you agree to
 * use this software also under the terms of the GPLv2.  
 * 
 * If you obtained your copy of db4o under a commercial license, then you may
 * use this software under the terms of the Apache license (or the GPLv2 license
 * if you so choose).
 * 
 * =============================================================================
 * GNU Public License Notice:
 * Db4o implementation of the WicketStuff persistence facade service provider
 * interface
 * Copyright (C) 2007 Tim Boudreau
 *
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * =============================================================================
 * Apache License Notice:
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
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
package org.wicketstuff.persistence.db4o;

import java.util.Comparator;
import org.wicketstuff.persistence.queries.RangeValue;
import org.wicketstuff.persistence.queries.StringEndsWithValue;
import org.wicketstuff.persistence.queries.StringStartsWithValue;
import org.wicketstuff.persistence.queries.CompoundQueryElement.Item;
import org.wicketstuff.persistence.spi.PersistenceUtils;
import org.wicketstuff.persistence.ActivationStrategy;
import org.wicketstuff.persistence.DbJob;
import org.wicketstuff.persistence.InvalidUuidException;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oUUID;
import com.db4o.ext.ObjectInfo;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.queries.StringContainsValue;
import org.wicketstuff.persistence.queries.*;

/**
 * Concrete implementation of PersistenceUtils for db4o - this is what does
 * the heavy lifting.
 *
 * @author Tim Boudreau
 */
final class Db4oPersistenceUtils implements PersistenceUtils {
    private final Db<ObjectContainer> database;
    Db4oPersistenceUtils(Db <ObjectContainer> database){
        this.database = database;
    }
    
    private Db<ObjectContainer> getDatabase() {
        return database;
    }
    
    private static final char UUID_PART_SEPARATOR = ':';
    /**
     * Convert a string encoded by uuidToString into a corresponding
     * uid.
     */ 
    public Db4oUUID stringToUUid(String s) throws InvalidUuidException {
        String[] parts = s.split("" + UUID_PART_SEPARATOR);
        if (parts.length != 2) {
            throw new InvalidUuidException("Uuid should contain exactly one ; character.  This " +
                    " string split into " + parts.length + " parts", s);
        }
        String uidPart = parts[0];
        String byteArrayPart = parts[1];
        byte[] bytes;
        try {
            bytes = stringToBytes(byteArrayPart);
        } catch (NumberFormatException e) {
            throw new InvalidUuidException ("Invalid hex string for byte array " +
                    "part: " + byteArrayPart, s, e);
        }
        long uid;
        try {
            uid = Long.parseLong(uidPart, 32);
        } catch (NumberFormatException e) {
            throw new InvalidUuidException ("Invalid base 32 string for byte array " +
                    "part: " + uidPart, s, e);
        }
        return new Db4oUUID (uid, bytes);
    }
    
    /**
     * Convert a Db4o unique id to a string representation.  A uuid
     * consists of a long id value and a byte array.  The result is
     * in the format
     * id_value;byte_array
     * The id is encoded as a base 32 string;  the byte array is an
     * arbitrary length hexadecimal string.
     * @param uuid A uuid
     * @throws InvalidUuidException if the uuid is null
     */ 
    public String uuidToString(Db4oUUID uuid) throws InvalidUuidException {
        if (uuid == null) {
            throw new InvalidUuidException (null);
        }
        byte[] bytes = uuid.getSignaturePart();
        long uidPart = uuid.getLongPart();
        StringBuilder result = new StringBuilder();
        result.append (Long.toString(uidPart, 32));
        result.append (UUID_PART_SEPARATOR);
        result.append (bytesToString(bytes));
        return result.toString();
    }
    
    /**
     * Convert a string of hex bytes into a byte array.
     * Throws a NumberFormatException given an invalid string.
     * Null or empty string returns an empty array.
     */ 
    private static byte[] stringToBytes(String s) {
        if (s == null || "".equals(s)) {
            return new byte[0];
        }
        final char[] c = s.toCharArray();
        assert c.length % 2 == 0;
        final byte[] result = new byte[c.length / 2];
        for (int i = 0; i < c.length; i += 2) {
            int ix = i / 2;
            result[ix] = byteVal(c[i]);
            result[ix] = (byte) (result[ix] << 4);
            byte high = byteVal(c[i + 1]);
            result[ix] |= high;
        }
        return result;
    }

    /**
     * Convert a character into a nibble.
     */ 
    private static byte byteVal(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            default:
                throw new NumberFormatException("Illegal character " + c);
        }
    }

    /**
     * Convert a byte array into a hexadecimal string
     */ 
    static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return "";
        }
        char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            result.append(table[((bytes[i] & 240) >>> 4 & 15)]);
            result.append(table[bytes[i] & 0x0F]);
        }
        return result.toString();
    }    
    
    private static final class UidLoader<T> implements DbJob<ObjectContainer, T, Long> {
        private final TypeChecker<T> checker;
        private final ActivationStrategy activation;
        private UidLoader (TypeChecker<T> checker, ActivationStrategy activation) {
            this.checker = checker;
            this.activation = activation;
        }
        
        @SuppressWarnings("unchecked")
        public T run(ObjectContainer db, Long uid) {
            @SuppressWarnings("unchecked")
            Object result = db.ext().getByID(uid);
            boolean rightType = checker.checkType(result);
            if (rightType && result != null) {
                db.activate(result, activation.getActivationDepth());
            }
            if (!rightType) {
                throw new ClassCastException (result.getClass().getName() + 
                        " is not an instance of " + checker.toString());
            }
            return (T) result;
        }
    }
    
    private static final class ObjectSaver<T> implements DbJob<ObjectContainer, Boolean, T> {
        public Boolean run(ObjectContainer db, T obj) {
            db.set(obj);
            db.commit();
            boolean result = db.ext().getObjectInfo(obj) != null;
            return result;
        }
    }
    
    private static final class ObjectSaverUid<T> implements DbJob<ObjectContainer, Long, T> {
        public Long run(ObjectContainer db, T obj) {
            db.set(obj);
            db.commit();
            long result = db.ext().getID(obj);
            ObjectSet<T> all = db.get(obj.getClass());
            return result;
        }
    }
    
    private static final class ObjectPrototypeCollectionFetcher<T> implements DbJob<ObjectContainer, List<T>, T> {
        public List<T> run(ObjectContainer db, T prototype) {
            ObjectSet<T> result = db.get(prototype);
            return Collections.unmodifiableList(new ArrayList<T>(result));
        }
    }
    
    private static final class ObjectAllCollectionFetcher<T> implements DbJob<ObjectContainer, List<T>, Class<T>> {
        public List<T> run(ObjectContainer db, Class<T> type) {
            ObjectSet<T> result = db.query(type);
            return Collections.unmodifiableList(new ArrayList<T>(result));
        }
    }
    
    public <T> List<T> fetchAllOfType(Class<T> clazz) {
        ObjectAllCollectionFetcher<T> fetcher = new ObjectAllCollectionFetcher<T>();
        return database.run(fetcher, clazz);
    }
    
    public <T> List<T> fetchByPrototype (T prototype) {
        ObjectPrototypeCollectionFetcher<T> fetcher = new ObjectPrototypeCollectionFetcher<T>();
        return database.run(fetcher, prototype);
    }
    
    private static final class UuidLoader<T> implements DbJob<ObjectContainer, T, Db4oUUID> {
        private final TypeChecker<T> checker;
        private final ActivationStrategy activation;
        private UuidLoader (TypeChecker<T> checker, ActivationStrategy activation) {
            this.checker = checker;
            this.activation = activation;
        }
        
        @SuppressWarnings("unchecked")
        public T run(ObjectContainer db, Db4oUUID uuid) {
            Object result = db.ext().getByUUID(uuid);
            boolean rightType = checker.checkType(result);
            if (rightType && result != null) {
                db.activate(result, activation.getActivationDepth());
            }
            if (!rightType) {
                throw new ClassCastException (result.getClass().getName() + 
                        " is not an instance of " + checker.toString());
            }
            return (T) result;
        }
    }

    private static final class TypeChecker<T> {
        private Class<T> type;
        private TypeChecker(Class<T> type) {
            this.type = type;
        }
        
        boolean checkType(Object o) {
            return o == null || o.getClass().equals(type) || 
                    o.getClass().isInstance(type);
        }
        
        @Override
        public String toString() {
            return type.getName();
        }
    }
    
    public <T> T fetchByUid(long uid, Class<T> type, ActivationStrategy activation) {
        UidLoader<T> loader = new UidLoader<T> (new TypeChecker<T>(type), activation);
        return getDatabase().run(loader, uid);
    }
    
    public <T> T fetchByUuid(String uuid, Class<T> type, ActivationStrategy activation) {
        Db4oUUID iuuid = stringToUUid(uuid); 
        UuidLoader<T> loader = new UuidLoader<T> (new TypeChecker<T>(type), activation);
        return getDatabase().run(loader, iuuid);
    }
    
    public <T> long saveObjectReturnUid(T object) {
        ObjectSaverUid<T> saver = new ObjectSaverUid<T>();
        return getDatabase().run (saver, object);
    }

    public <T> boolean saveObject(T object) {
        ObjectSaver<T> saver = new ObjectSaver<T>();
        return getDatabase().run(saver, object);
    }
    
    private static final class UidFetcher implements DbJob<ObjectContainer, Long, Object> {
        public Long run(ObjectContainer db, Object obj) {
            return db.ext().getID(obj);
        }
    }
    
    private static final class VersionFetcher implements DbJob<ObjectContainer, Long, Object> {
        public Long run(ObjectContainer db, Object obj) {
            ObjectInfo info = db.ext().getObjectInfo(obj);
            return info == null ? -1 : info.getVersion();
        }
    }
    
    public long getUid(Object o) {
        UidFetcher fetcher = new UidFetcher();
        return getDatabase().run(fetcher, o);
    }
    
    public long getVersion(Object o) {
        VersionFetcher fetcher = new VersionFetcher();
        return getDatabase().run(fetcher, o);
    }

    private static final class UuidFetcher implements DbJob<ObjectContainer, Db4oUUID, Object> {
        public Db4oUUID run(ObjectContainer db, Object obj) {
            ObjectInfo info = db.ext().getObjectInfo(obj);
            return info == null ? null : info.getUUID();
        }
    }
    
    public String getUuid(Object o) {
        UuidFetcher fetcher = new UuidFetcher();
        Db4oUUID uuid = getDatabase().run(fetcher, o);
        return uuidToString(uuid);
    }
    
    private static final class AnyFetcher<T> implements DbJob<ObjectContainer, T, Class<T>> {
        public T run(ObjectContainer container, Class<T> type) {
            ObjectSet<T> set = container.query(type);
            return set.isEmpty() ? null : set.iterator().next();
        }
    }
    
    public <T> T fetchOneOfType(Class<T> type) {
        AnyFetcher<T> f = new AnyFetcher<T>();
        return getDatabase().run(f, type);
    }

    private static final class SingleDeleter implements DbJob<ObjectContainer, Boolean, Object> {
        public Boolean run(ObjectContainer container, Object obj) {
            container.delete(obj);
            container.commit();
            return container.ext().getObjectInfo(obj) == null;
        }
    }
    
    private static final class AllOfTypeDeleter<T> implements DbJob<ObjectContainer, Boolean, Class<T>> {
        public Boolean run(ObjectContainer container, Class<T> type) {
            ObjectSet<T> set = container.query(type);
            for (T t : set) {
                container.delete(t);
            }
            return set.isEmpty();
        }
    }
    
    public boolean delete(Object obj) {
        SingleDeleter d = new SingleDeleter();
        return getDatabase().run(d, obj);
    }

    public <T> boolean deleteAllOfType(Class<T> clazz) {
        AllOfTypeDeleter<T> d = new AllOfTypeDeleter<T>();
        return getDatabase().run(d, clazz);
    }

    private static final class QueryFinder<T, P> implements DbJob<ObjectContainer, List<T>, Void> {
        private final String field;
        private final Class<T> type;
        private final Class<P> fieldType;
        private final P fieldValue;
        QueryFinder (Class<T> type, String field, Class<P> fieldType, P fieldValue) {
            this.type = type;
            this.field = field;
            this.fieldType = fieldType;
            this.fieldValue = fieldValue;
        }

        public List<T> run(ObjectContainer db, Void ignored) {
            Query q = db.query();
            q.constrain(type);
            q.descend(field).constrain(fieldValue);
            @SuppressWarnings("unchecked")
            ObjectSet<T> set = q.execute();
            return new ArrayList<T>(set);
        }
    }
    
    public <T, P> T fetchByFieldQuery(Class<T> type, Class<P> fieldType, String fieldName, P fieldValue) {
        QueryFinder<T, P> f = new QueryFinder<T, P>(type, fieldName, fieldType, fieldValue);
        List<T> results = getDatabase().run(f, null);
        return results.isEmpty() ? null : results.iterator().next();
    }

    public <T, P> List<T> fetchAllByQuery(Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, Comparator<T> compare) {
        QueryFinder<T, P> f = new QueryFinder<T, P>(type, fieldName, fieldType, fieldValue);
        List<T> results = getDatabase().run(f, null);
        if (compare != null) {
            Collections.sort(results, compare);
        }
        return results;
    }
    
    private static final class ComplexQueryFinder<T> implements DbJob<ObjectContainer, Collection<T>, QueryElement> {
        private final Class<T> type;
        private final String txt;
        ComplexQueryFinder (Class<T> type, String txt) {
            this.type = type;
            this.txt = txt;
        }
        public Collection<T> run(ObjectContainer db, QueryElement qe) {
            Collection<T> result = null;
            Query q = db.query();
            Constraint constraint = q.constrain(type);
            recurseQuery (q, qe, Logic.AND, q.constraints());
            result = q.execute();
//            System.err.println("RESULT: " + result);
            if (result == null) {
                result = Collections.<T>emptyList();
            }
            return result;
        }

        private Constraint recurseQuery (Query query, QueryElement qe, Logic op, Constraint current) {
            Constraint result = current;
//            System.out.println("Recurse query on " + qe + " operation " + op);
            if (qe instanceof FieldQueryElement ) {
                FieldQueryElement fd = ( FieldQueryElement ) qe;
//                System.out.println("  It is a field descriptor");
                String nm = fd.name;
                String[] descent = nm.split("\\.");
                Query workingQuery = query;
                for (String s : descent) {
                    workingQuery = workingQuery.descend(s);
//                    System.out.println("Descend through " + s + " to " + workingQuery);
                }
                Object value = fd.value;
                boolean neg = fd.isNegated();
                if (value instanceof RangeValue ) {
                    RangeValue range = ( RangeValue ) value;
                    result = workingQuery.constrain(range.start).greater().equal();
                    result = result.and(workingQuery.constrain(range.finish).smaller().equal());
                    if (neg) {
                        result = result.not();
                    }
                } else if (value instanceof StringContainsValue ) {
                    StringContainsValue sc = ( StringContainsValue ) value;
                    result = workingQuery.constrain(sc.text).contains();
                    if (neg) {
                        result = result.not();
                    }
                } else if (value instanceof StringStartsWithValue ) {
                    StringStartsWithValue ssw = ( StringStartsWithValue ) value;
                    result = workingQuery.constrain(ssw.text).like();
                    if (neg) {
                        result = result.not();
                    }
                } else if (value instanceof StringEndsWithValue ) {
                    StringEndsWithValue sew = ( StringEndsWithValue ) value;
                    result = workingQuery.constrain(sew.text).endsWith(!sew.ignoreCase);
                    if (neg) {
                        result = result.not();
                    }
                } else {
                    if (neg) {
                        switch (op) {
                            case AND :
    //                            System.out.println("  Negated AND constrain to " + value);
                                result = workingQuery.constrain(value).not();//current.not().and(workingQuery.constrain(value));
                                break;
                            case OR :
    //                            System.out.println("  Negated OR constrain to " + value);
                                result = current.or(workingQuery.constrain(value).not());
                                break;
                            default :
                                throw new AssertionError();
                        }
                    } else {
                        switch (op) {
                            case AND :
    //                            System.out.println("  AND constrain to " + value);
                                result = current.and(workingQuery.constrain(value));//current.and(workingQuery.constrain(value));
                                break;
                            case OR :
    //                            System.out.println("  OR constrain to " + value);
                                result = current.or(workingQuery.constrain(value));
                                break;
                            default :
                                throw new AssertionError();
                        }
                    }
                }
            } else if (qe instanceof CompoundQueryElement ) {
//                System.out.println("Process query element group " + qe);
                CompoundQueryElement qeg = ( CompoundQueryElement ) qe;
                Constraint workingConstraint = current;
//                System.out.println("  Loop and unwrap");
                for (Iterator<Item> it=qeg.iterator(); it.hasNext();) {
                    Item item = it.next();
                    Logic operation = item.op;
//                    System.err.println("  Process " + item.element);
                    Constraint nue = 
                        recurseQuery (query, item.element, operation, workingConstraint);
//                    System.err.println("Processed " + item + " iterator has next " + it.hasNext() + " query result " + query.execute());
                    if (workingConstraint == current) {
                        workingConstraint = nue;
                    }
                }
            }
            return result;
        }
        
        /*
        private Constraint recurseQuery (Query query, QueryElement qe, Logic op, Constraint current) {
            if (qe instanceof FieldQueryElement) {
                FieldQueryElement fd = (FieldQueryElement) qe;
                String nm = fd.name;
                String[] descent = nm.split(".");
                Query workingQuery = query;
                if (descent.length > 1) {
                    for (int i=0; i < descent.length; i++) {
                        workingQuery = query.descend(descent[i]);
                    }
                }
                Constraint constraint = current;
                Object value = fd.value;
                if (value instanceof FieldQueryElement.Range) {
                    FieldQueryElement.Range range = (Range) value;
                    constraint = workingQuery.constrain(range.start).greater().equal();
                    constraint = constraint.and(workingQuery.constrain(range.finish).smaller().equal());
                } else if (value instanceof StringContainsValue) {
                    StringContainsValue sc = (StringContainsValue) value;
                    constraint = workingQuery.constrain(sc.text).contains();
                } else if (value instanceof StringStartsWithValue) {
                    StringStartsWithValue ssw = (StringStartsWithValue) value;
                    constraint = workingQuery.constrain(ssw.text).like();
                } else if (value instanceof StringEndsWithValue) {
                    StringEndsWithValue sew = (StringEndsWithValue) value;
                    constraint = workingQuery.constrain(sew.text).endsWith(!sew.ignoreCase);
                } else {
                    constraint = workingQuery.constrain(fd.value).equal();
                }
                if (qe.isNegated()) {
                    constraint = constraint.not();
                }
                
                return constraint;
            } else {
                CompoundQueryElement grp = (CompoundQueryElement) qe;
                boolean first = true;
                Constraint workingConstraint = current;
                for (Object sub : grp) {
                    CompoundQueryElement.Item item = (Item) sub;
                    Constraint nue = recurseQuery (query, item.element, item.op, workingConstraint);
                    boolean neg = item.element.isNegated();
                    switch (item.op) {
                        case AND :
                            if (neg) {
                                nue = workingConstraint.and(nue).not();
                            } else {
                                nue = workingConstraint.and(nue);
                            }
                            break;
                        case OR :
                            if (neg) {
                                nue = workingConstraint.or(nue).not();
                            } else {
                                nue = workingConstraint.or(nue);
                            }
                            break;
                    }
                    if (first) {
                        workingConstraint = nue;
                    }
                }
                return query.constraints();
            }
        }
        
        public String toString() {
            return "Complex query: " + txt;
        }
    }
         */ 

    }

    public <T> Collection<T> fetchByComplexQuery(Class<T> expectedType, QueryElement<T> query) {
        ComplexQueryFinder<T> f = new ComplexQueryFinder<T>(expectedType, query.toString());
        Collection<T> results = getDatabase().run (f, query);
        return results;
    }
}
