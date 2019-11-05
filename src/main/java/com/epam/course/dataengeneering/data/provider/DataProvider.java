package com.epam.course.dataengeneering.data.provider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface DataProvider {

    Stream<Stream<String>> batchRead(Path path, int limit) throws IOException;

}
