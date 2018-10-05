package com.github.jscancella.verify.internal;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jscancella.internal.PathUtils;

/**
 * Implements {@link SimpleFileVisitor} to ensure that the encountered file is in one of the manifests.
 */
abstract public class AbstractPayloadFileExistsInManifestsVistor extends SimpleFileVisitor<Path> {
  protected static final Logger logger = LoggerFactory.getLogger(AbstractPayloadFileExistsInManifestsVistor.class);
  protected static final ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  protected transient final boolean ignoreHiddenFiles;

  public AbstractPayloadFileExistsInManifestsVistor(final boolean ignoreHiddenFiles) {
    this.ignoreHiddenFiles = ignoreHiddenFiles;
  }
  
  @Override
  public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
    if(ignoreHiddenFiles && PathUtils.isHidden(dir) || dir.endsWith(Paths.get(".bagit"))){
      logger.debug(messages.getString("skipping_hidden_file"), dir);
      return FileVisitResult.SKIP_SUBTREE;
    }
    
    return FileVisitResult.CONTINUE;
  }
}
