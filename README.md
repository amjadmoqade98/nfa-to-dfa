[![Code Grade](https://www.code-inspector.com/project/16552/score/svg)](https://frontend.code-inspector.com/public/project/16552/NdfsaToDfsa/dashboard)
[![Code Grade](https://www.code-inspector.com/project/16552/status/svg)](https://frontend.code-inspector.com/public/project/16552/NdfsaToDfsa/dashboard)


# Nondeterministic finite states To Minimized Deterministic finite states  


## Steps
1. read Nondeterministic finite states from a file <br/><br/>
>    **File template:** 
>     <br/><br/>
>           S A B C D E F G H&ensp;&ensp;&ensp;first line  : States<br/>
>           H&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;second line :Final State<br/>
>           l d @ &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;thirds line :letters<br/>
>           S A l&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;next lines : transitions between states<br/>
>           A B @<br/>
>          .<br/>
>          .<br/>

2. convert Nondeterministic finite states to  Deterministic  using the tabular method .
3. minimize the states using the feasible-pairs table.
4. validate if giving strings from the user belonging to the language or not.



  

## How to run
1. place the data in the resources/data.txt file
2. run the Main.java class


## Test case:

file: <br/>
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/file.png" />


Console After Running: <br/>

<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/ndfsa.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/lambda.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/nonD.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/Rename.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/nonAc.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/minimize.png" />
<img src="https://github.com/AmjadMoqade98/NdfsaToDfsa/blob/main/src/resources/images/validate.png" />

