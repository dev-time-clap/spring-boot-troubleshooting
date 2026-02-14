package de.devtime.examples.library.persistence.util;

import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class TransactionHelper {

  private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

  private final PlatformTransactionManager txManager;

  @Setter(onMethod_ = @Value("${libraryDemo.transaction.namingStrategy:UUID}"))
  private String namingStrategy;

  public void executeInTxWithoutResult(final Runnable runnable) {
    executeInTxWithoutResult(Propagation.REQUIRED, Isolation.DEFAULT, false, _ -> runnable.run());
  }

  public void executeInTxWithoutResult(
      final Propagation propagation,
      final Runnable runnable) {
    executeInTxWithoutResult(propagation, Isolation.DEFAULT, false, _ -> runnable.run());
  }

  public void executeInTxWithoutResult(final Consumer<TransactionStatus> txStatusConsumer) {
    executeInTxWithoutResult(Propagation.REQUIRED, Isolation.DEFAULT, false, txStatusConsumer);
  }

  public void executeInTxWithoutResult(
      final Propagation propagation,
      final Consumer<TransactionStatus> txStatusConsumer) {
    executeInTxWithoutResult(propagation, Isolation.DEFAULT, false, txStatusConsumer);
  }

  public void executeInTxWithoutResult(
      final Propagation propagation,
      final Isolation isolation,
      final boolean readOnly,
      final Consumer<TransactionStatus> txStatusConsumer) {
    TransactionTemplate txTemplate = new TransactionTemplate(this.txManager);
    txTemplate.setIsolationLevel(isolation.value());
    txTemplate.setPropagationBehavior(propagation.value());
    txTemplate.setReadOnly(readOnly);
    txTemplate.setName(getTxName());
    txTemplate.executeWithoutResult(txStatusConsumer);
  }

  public <T> T executeInTx(final TransactionCallback<T> txCallable) {
    return executeInTx(Propagation.REQUIRED, Isolation.DEFAULT, false, txCallable);
  }

  public <T> T executeInTx(
      final Propagation propagation,
      final TransactionCallback<T> txCallable) {
    return executeInTx(propagation, Isolation.DEFAULT, false, txCallable);
  }

  public <T> T executeInTx(
      final Propagation propagation,
      final Isolation isolation,
      final boolean readOnly,
      final TransactionCallback<T> txCallable) {
    TransactionTemplate txTemplate = new TransactionTemplate(this.txManager);
    txTemplate.setIsolationLevel(isolation.value());
    txTemplate.setPropagationBehavior(propagation.value());
    txTemplate.setReadOnly(readOnly);
    txTemplate.setName(getTxName());
    return txTemplate.execute(txCallable);
  }

  private String getTxName() {
    return switch (this.namingStrategy) {
      case "uuid" -> UUID.randomUUID().toString();
      case "class-and-method" -> resolveCaller();
      default -> UUID.randomUUID().toString();
    };
  }

  private String resolveCaller() {
    return STACK_WALKER.walk(stream -> stream
        .filter(frame -> !frame.getClassName().equals(TransactionHelper.class.getName()))
        .findFirst()
        .map(frame -> frame.getDeclaringClass().getName() + "." + frame.getMethodName())
        .orElse(UUID.randomUUID().toString()));
  }
}
