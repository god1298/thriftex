## thriftex

A thrift extention library for java and work with spring framework.

## Features

* Easy start a server with spring bean config
* Init a sync thrift client with only a few spring bean config
* Thrift connection mananger with commons-pool

## How to use
* Config a thrift server with spring

```
//config a server handler bean which you implements
<bean id="your handler" class="your handler class which implements xxx.Iface">
</bean>
//config a thrift server bean
<bean id="server id" class="com.ximalaya.thrift.server.ThriftServer">
    // ref pre handler you config
    <property name="handler" ref="your handler"></property>
    // simple/threadpool/nonblocking/threadselected/hsha,default is threadselected
	<property name="serverType" value="simple"></property>
	// protocol can be json,binary,compact,default is compact
	<property name="protocolType" value="json"></property>
	 // which port server listened
	<property name="port" value="9091"></property>
	// worker thread size
	<property name="workerSize" value="10"></property>
	// selector thread size
	<property name="selectorSize" value="1"></property>
</bean>

// config a multiplexed thrift server bean

```

* Config a thrift client with spring

```
// config a client config
<bean id="clientConfig" class="com.ximalaya.thrift.client.ClientConfig">
	// thrift generate client class
	<property name="clientClass" value="com.ximalaya.thrift.test.TestService.Client"></property>
	// server domain or ip
	<property name="host" value="127.0.0.1"></property>
	// server port
	<property name="port" value="9001"></property>
	// timeout in millisecond
	<property name="timeout" value="60000"></property>
	// protocol can be json,binary,compact,default is compact
	<property name="protocolType" value="binary"></property>
	// if server type is nonblocking/threadselected/hsha set true, or else set false, default is true
	<property name="framed" value="true"></property>
	// if server is multiplexed set true,or else set false, default is false
	<property name="multiplexed" value="false"></property>
</bean>

// config client factory

<bean id="testServiceClientFactory" class="com.ximalaya.thrift.client.ThriftConnectionFactory">
	<property name="clientConfig" ref="clientConfig"></property>
	<property name="thriftClientPoolConfig">
		// pool config like commons pool
		<bean class="com.ximalaya.thrift.client.ThriftConnectionPoolConfig">
			<property name="maxActive" value="10"></property>
			<property name="minIdle" value="10"></property>
			<property name="maxIdle" value="10"></property>
			<property name="maxWait" value="10"></property>
		</bean>
	</property>
</bean>
```

