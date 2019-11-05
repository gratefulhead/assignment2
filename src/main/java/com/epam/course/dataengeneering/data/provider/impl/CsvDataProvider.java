package com.epam.course.dataengeneering.data.provider.impl;

import com.epam.course.dataengeneering.data.provider.DataProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class CsvDataProvider implements DataProvider {

    @Override
    public Stream<Stream<String>> batchRead(Path path, int limit) throws IOException {
        final long totalLines = Files.lines(path).count();
        return LongStream.range(0, (totalLines + limit - 1) / limit)
                .map(it -> it * limit)
                .mapToObj(offset -> {
                    try {
                        if (offset == 0) {
                            offset++;
                        }
                        return Files.lines(path).skip(offset).limit(limit);
                    } catch (IOException e) {
                        throw new RejectedExecutionException(e);
                    }
                });
    }
}
