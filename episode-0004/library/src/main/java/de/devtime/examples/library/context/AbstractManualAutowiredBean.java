package de.devtime.examples.library.context;

import org.springframework.context.ApplicationContext;

public abstract class AbstractManualAutowiredBean<B> {

  protected ApplicationContext appContext;

  public B autowire() {
    ApplicationContextProvider.findApplicationContext()
        .ifPresent(context -> {
          this.appContext = context;
          this.appContext.getAutowireCapableBeanFactory().autowireBean(this);
        });
    return self();
  }

  @SuppressWarnings("unchecked")
  protected B self() {
    return (B) this;
  }
}
