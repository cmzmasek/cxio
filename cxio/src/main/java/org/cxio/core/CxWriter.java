package org.cxio.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.aspects.writers.CartesianLayoutFragmentWriter;
import org.cxio.aspects.writers.EdgesFragmentWriter;
import org.cxio.aspects.writers.NodesFragmentWriter;
import org.cxio.aspects.writers.WriterUtils;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;
import org.cxio.util.JsonWriter;
import org.cxio.util.Util;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class is for writing aspect fragments (lists of aspects).
 *
 * @author cmzmasek
 *
 */
public class CxWriter {

    private final JsonWriter                        _jw;
    private boolean                                 _started;
    private final String                            _time_stamp;
    private boolean                                 _fragment_started;
    private final Map<String, AspectFragmentWriter> _writers;
    private final Map<String, String>               _aspect_name_to_time_stamp_map;

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out) throws IOException {
        return new CxWriter(out, false);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        return new CxWriter(out, use_default_pretty_printer);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param time_stamp the default time stamp used by all AspectFragmentWriters
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        return new CxWriter(out, use_default_pretty_printer, time_stamp);
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param aspect_writers the set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstance(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out.
     * <br>
     * Subsequent calls to method {@link #addAspectFragmentWriter(AspectFragmentWriter writer)} are
     * required to add {@link org.cxio.core.interfaces.AspectFragmentWriter} to the newly created CxWriter.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param time_stamp the default time stamp used by all AspectFragmentWriters
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance(time_stamp));
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with AspectFragmentWriters for nodes, edges, and cartesian layout elements
     * already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with AspectFragmentWriters for nodes, edges, and cartesian layout elements
     * already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param aspect_writers the (additional) set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @param time_stamp the default time stamp used by all AspectFragmentWriters
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers, final String time_stamp)
            throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance(time_stamp));
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance(time_stamp));
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with AspectFragmentWriters for nodes, edges, and cartesian layout elements
     * already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param aspect_writers  the (additional) set of {@link org.cxio.core.interfaces.AspectFragmentWriter} to use
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceNEC(final OutputStream out, final boolean use_default_pretty_printer, final Set<AspectFragmentWriter> aspect_writers) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        for (final AspectFragmentWriter aspect_writer : aspect_writers) {
            w.addAspectFragmentWriter(aspect_writer);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with all AspectFragmentWriters implemented in this library already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @param time_stamp the default time stamp used by all AspectFragmentWriters
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceWithAllAvailableWriters(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer, time_stamp);
        for (final AspectFragmentWriter afw : Util.getAllAvailableAspectFragmentWriters(time_stamp)) {
            w.addAspectFragmentWriter(afw);
        }
        return w;
    }

    /**
     * Returns a CxWriter for reading from OutputStream out with all AspectFragmentWriters implemented in this library already added.
     *
     * @param out the OutputStream to read
     * @param use_default_pretty_printer to turn pretty printing on/off
     * @return a CxWriter writer
     * @throws IOException
     */
    public final static CxWriter createInstanceWithAllAvailableWriters(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        final CxWriter w = new CxWriter(out, use_default_pretty_printer);
        for (final AspectFragmentWriter afw : Util.getAllAvailableAspectFragmentWriters(null)) {
            w.addAspectFragmentWriter(afw);
        }
        return w;
    }

    /**
     * This method is for adding a {@link org.cxio.core.interfaces.AspectFragmentWriter} to this CxWriter.
     *
     * @param writer the AspectFragmentWrite to add
     */
    public void addAspectFragmentWriter(final AspectFragmentWriter writer) {
        if (writer == null) {
            throw new IllegalArgumentException("aspect fragment writer is null");
        }
        if (Util.isEmpty(writer.getAspectName())) {
            throw new IllegalArgumentException("aspect name is null or empty");
        }

        _writers.put(writer.getAspectName(), writer);
    }

    /**
     * This method is to be called prior to writing individual aspect elements of a given type/name.
     *
     * @param aspect_name the name of the aspect elements to be written
     * @throws IOException
     */
    public void startAspectFragment(final String aspect_name) throws IOException {
        startAspectFragment(aspect_name, null);
    }

    /**
     * This method is to be called prior to writing individual aspect elements of a given type/name.
     * <br>
     * If the time stamp  argument is not null and not empty it will overwrite any time stamp set in
     * the createInstance methods.
     *
     * @param aspect_name the name of the aspect elements to be written
     * @param time_stamp the time stamp for the aspect elements to be written
     * @throws IOException
     */
    public void startAspectFragment(final String aspect_name, final String time_stamp) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("fragment already started");
        }
        boolean have_time_stamp = false;
        if (!Util.isEmpty(time_stamp)) {
            have_time_stamp = true;
            if (_aspect_name_to_time_stamp_map.containsKey(aspect_name)) {
                final String prev_time_stamp = _aspect_name_to_time_stamp_map.get(aspect_name);
                if (!Util.isEmpty(prev_time_stamp)) {
                    if (prev_time_stamp.equals(time_stamp)) {
                        have_time_stamp = false;
                    }
                    else {
                        throw new IllegalStateException("illegal attempt to set multiple, different time stamps for aspect '" + aspect_name + "': '" + prev_time_stamp + "', '" + time_stamp + "'");
                    }
                }
            }
        }
        _fragment_started = true;
        _jw.startArray(aspect_name);
        if (have_time_stamp) {
            WriterUtils.writeTimeStamp(time_stamp, _jw);
            _aspect_name_to_time_stamp_map.put(aspect_name, time_stamp);
        }
        else if (!Util.isEmpty(_time_stamp) && !_aspect_name_to_time_stamp_map.containsKey(aspect_name)) {
            WriterUtils.writeTimeStamp(_time_stamp, _jw);
            _aspect_name_to_time_stamp_map.put(aspect_name, _time_stamp);
        }
    }

    /**
     * This method is to be called after writing individual aspect elements of a given type/name.
     *
     * @throws IOException
     */
    public void endAspectFragment() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }

        _fragment_started = false;
        _jw.endArray();
    }

    /**
     * This method is to be called at the end of writing to a stream.
     *
     * @throws IOException
     */
    public void end() throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        _started = false;
        _jw.end();
    }

    /**
     *  This method is to be called at the beginning of writing to a stream.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        if (_started) {
            throw new IllegalStateException("already started");
        }
        _started = true;
        _jw.start();
    }

    /**
     * This is for writing a list of AspectElements (a aspect fragment) of a given type.
     * <br>
     * A appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter} will be automatically
     * selected (if added previously).
     *
     *
     * @param elements the list of AspectElements to be written
     * @throws IOException
     */
    public void writeAspectElements(final List<AspectElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (_writers.containsKey(elements.get(0).getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(elements.get(0).getAspectName());
            if (!Util.isEmpty(_time_stamp) && Util.isEmpty(writer.getTimeStamp())) {
                writer.setTimeStamp(_time_stamp);
            }
            writer.write(elements, _jw);
        }
    }

    /**
     * This is for writing a list of AspectElements (a aspect fragment) of a given type.
     *
     * @param elements the list of AspectElements to be written
     * @param writer a appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter}
     * @throws IOException
     */
    public void writeAspectElements(final List<AspectElement> elements, final AspectFragmentWriter writer) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        if (!Util.isEmpty(_time_stamp) && Util.isEmpty(writer.getTimeStamp())) {
            writer.setTimeStamp(_time_stamp);
        }
        writer.write(elements, _jw);
    }

    /**
     * This is for writing a single AspectElement.
     * A appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter} will be automatically
     * selected (if added previously).
     * <br>
     * Prior to calling this method for a AspectElement of a given type/name,
     * {@link #startAspectFragment(String aspect_name)} needs to be called.
     * <br>
     * After all AspectElements of a given type/name a written, {@link #endAspectFragment()} needs to be called.
     *
     * @param element the AspectElements to be written
     * @throws IOException
     */
    public void writeAspectElement(final AspectElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }
        if (element == null) {
            return;
        }
        if (_writers.containsKey(element.getAspectName())) {
            final AspectFragmentWriter writer = _writers.get(element.getAspectName());
            writer.writeElement(element, _jw);
        }
    }

    /**
     * This is for writing a single AspectElement.
     * <br>
     * Prior to calling this method for a AspectElement of a given type/name,
     * {@link #startAspectFragment(String aspect_name)} needs to be called.
     * <br>
     * After all AspectElements of a given type/name a written, {@link #endAspectFragment()} needs to be called.
     *
     * @param element the AspectElements to be written
     * @param writer a appropriate {@link org.cxio.core.interfaces.AspectFragmentWriter}
     * @throws IOException
     */
    public void writeAspectElement(final AspectElement element, final AspectFragmentWriter writer) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (!_fragment_started) {
            throw new IllegalStateException("fragment not started");
        }

        if (element == null) {
            return;
        }
        writer.writeElement(element, _jw);
    }

    /**
     * This is for writing a single {@link org.cxio.aspects.datamodels.AnonymousElement}.
     *
     * @param element the AnonymousElement to be written
     * @throws IOException
     */
    public void writeAnonymousAspectElement(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }

        if (element == null) {
            return;
        }
        if (_fragment_started) {
            _jw.writeJsonObject(element.getData());
        }
        else {
            _jw.writeJsonObject(element.getAspectName(), element.getData());
        }
    }

    /**
     * This is for writing a single {@link org.cxio.aspects.datamodels.AnonymousElement}
     * as the single member of Json array.
     *
     *
     * @param element  the AnonymousElement to be written
     * @throws IOException
     */
    public void writeAnonymousAspectElementAsList(final AnonymousElement element) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if (element == null) {
            return;
        }
        _jw.writeJsonObjectAsList(element.getAspectName(), element.getData());
    }

    /**
     * This is for writing a list of {@link org.cxio.aspects.datamodels.AnonymousElement}.
     *
     * @param elements the AnonymousElements to be written
     * @throws IOException
     */
    public void writeAnonymousAspectElements(final List<AnonymousElement> elements) throws IOException {
        if (!_started) {
            throw new IllegalStateException("not started");
        }
        if (_fragment_started) {
            throw new IllegalStateException("in individual elements writing state");
        }
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        final List<ObjectNode> datas = new ArrayList<ObjectNode>();
        for (final AnonymousElement elem : elements) {
            datas.add(elem.getData());
        }
        _jw.writeJsonObjects(elements.get(0).getAspectName(), datas);
    }

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        _time_stamp = null;
        _started = false;
        _fragment_started = false;
        _aspect_name_to_time_stamp_map = new HashMap<String, String>();
    }

    private CxWriter(final OutputStream out, final boolean use_default_pretty_printer, final String time_stamp) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("output stream is null");
        }
        _writers = new HashMap<String, AspectFragmentWriter>();
        _jw = JsonWriter.createInstance(out, use_default_pretty_printer);
        if (!Util.isEmpty(time_stamp)) {
            _time_stamp = time_stamp;
        }
        else {
            _time_stamp = null;
        }
        _started = false;
        _fragment_started = false;
        _aspect_name_to_time_stamp_map = new HashMap<String, String>();
    }

}
