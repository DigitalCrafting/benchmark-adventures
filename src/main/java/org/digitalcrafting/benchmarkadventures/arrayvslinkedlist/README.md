# ArrayList vs LinkedList 

## Results

```
Benchmark                      Mode  Cnt          Score          Error  Units
arrayList_findByReference      avgt    5    2696499.728 ±    32202.502  ns/op
linkedList_findByReference     avgt    5    7028258.295 ±   411854.774  ns/op

arrayList_getByKnownIndex      avgt    5          1.355 ±        0.020  ns/op
linkedList_getByKnownIndex     avgt    5   14109357.750 ±  4523485.134  ns/op

arrayList_iterateOver          avgt    5    4241855.707 ±    51949.429  ns/op
linkedList_iterateOver         avgt    5   18701734.963 ±   153427.714  ns/op

arrayList_removeByKnownIndex   avgt    5      23807.499 ±    22832.530  ns/op
linkedList_removeByKnownIndex  avgt    5   13611894.721 ±   415849.451  ns/op

arrayList_removeByReference    avgt    5    5446367.115 ±   116991.612  ns/op
linkedList_removeByReference   avgt    5   13715672.076 ±  1153278.238  ns/op

arrayList_addFirst               ss    5    3220312.800 ±   428670.631  ns/op
linkedList_addFirst              ss    5       8515.000 ±    24870.360  ns/op

arrayList_addInTheMiddle         ss    5     815357.800 ±   282617.391  ns/op
linkedList_addInTheMiddle        ss    5  142979327.200 ± 18151974.063  ns/op

arrayList_addLast                ss    5       4606.600 ±    23419.256  ns/op
linkedList_addLast               ss    5       1620.400 ±      680.293  ns/op

arrayList_removeFirst            ss    5    1933858.000 ±   127364.646  ns/op
linkedList_removeFirst           ss    5       5216.600 ±      713.334  ns/op

arrayList_removeInTheMiddle      ss    5     824331.600 ±    53493.129  ns/op
linkedList_removeInTheMiddle     ss    5  143313481.400 ± 34759711.042  ns/op

arrayList_removeLast             ss    5       3943.600 ±      834.749  ns/op
linkedList_removeLast            ss    5       5973.800 ±      972.878  ns/op
```
