# geppetto-proxies

A library of classes that will make it easier to manipulate and build
responses for Fabric services.

The idea is to use the [Fluent Interface Pattern](https://en.wikipedia.org/wiki/Fluent_interface)
in order to make it easier to manipulate and build out responses
by chaining methods and producing less verbose code —e.g.:

```
return new ResultProxy()
.addDataset(new DatasetProxy("d1")
	.addRecord(new RecordProxy()
			.addParam("foo", "foo-1")
			.addParam("bar", 1.13f)
	)
	.addRecord(new RecordProxy()
			.addParam("foo", "foo-2")
			.addParam("bar", 1.23f)
	)
).getResult();
```

This will return:

```
{
	"d1": [
		{"foo": "foo-1", "bar", 1.13},
		{"foo": "foo-2", "bar", 1.23}
	]
}
```
