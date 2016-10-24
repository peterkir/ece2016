# ece2016
EclipseCon Europe 2016 - Tutorial - Building Nano Services with OSGi Declarative Services

[![Gitter](https://badges.gitter.im/peterkir/ece2016.svg)](https://gitter.im/peterkir/ece2016?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

https://www.eclipsecon.org/europe2016/session/building-nano-services-osgi-declarative-services

## Tooling
- Java 8
- Eclipse EPP committer package Oxygen M2
- bndtools 3.3
- Github Repository
- Gitter Chat

### full-automated setup of IDEfix Eclipse product and this Github project 
- [IDEfix Eclipse Installation Guide](https://peterkir.github.io/idefix/bootstrap/conference/ece2016/)

### oomph/eclipseInstaller product and project available
- [oomph product.setup file](http://peterkir.github.io/oomph/public/ece2016/product-osgi.idefix.oxygen-ece2016.setup)
- [oomph project.setup file](http://peterkir.github.io/oomph/public/ece2016/project-ece2016.setup)


## Tutorial content
This tutorial is showing the usage of OSGi declarative services.
It contains projects for Eclipse PDE (Plugin Development Environment) and as bnd/bndtools.

- API definition <code>examples.&lt;pde|bndtools&gt;.service.api</code>
- simple OSGi service implementation named <code>examples.&lt;pde|bndtools&gt;.inverter</code>
- configurable OSGi service implementation name <code>examples.&lt;pde|bndtools&gt;.configurable</code>

It explains debugging via OSGi console, Apache Webconsole
