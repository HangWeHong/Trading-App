# Group10

This is a repository of Data Strcuture Assignment for Group 10 students ❤❤

```java
public class Teamwork {
    public static void main(String[] args) {
        System.out.println("Teamwork makes the dream works");
    }
}
```

## How to use

For those who are not familliar with github,you might wanna try to watch this [tutorial video](https://www.youtube.com/watch?v=RGOj5yH7evk),and u can make the playback speed x2 so this video will only take about half an hour for you to learn all the git commands and basic knowledge!Gamabateh!!But anyway i will still put some steps that those who are unfamilliar with git can follow.

## Add your files
Before doing anything ,make sure you downloaded [git](https://www.atlassian.com/git/tutorials/install-git?section=git-for-mac-installer)

You can add your files by:

- Create or upload files using github
- Add files using the command line when you have learned all the [git commands](https://gist.github.com/gwenf/19e5748a5391929e8e938a22c8a4b3f2)


## Git Tutorial

### Setup

You can clone this project by running the following command:

```shell
git clone https://github.com/HangWeHong/Trading-App.git
```

![](image.png)

Then, checkout to a new branch using:

```shell
git checkout -b <branch-name>
```

As an example,

![](docs/readme02.PNG)

After that, create a directory named `matricNo_name` and store your files within it.
You can now add the files to the repository:

### Add files

```shell
makedir <matricNo_name>
git add <matricNo_name>/*
```

![](docs/readme03.PNG)

For this tutorial, I have added two files `a random file.txt` and `put your files here.txt`.
To verify that the files are added successfully, you may use the command:

```shell
git status
```

![](docs/readme04.PNG)

### Commit (Save) files

You can then commit the files using:

```shell
git commit -m "<commit-message>"
```

![](docs/readme05.PNG)

To verify that you made a commit, you may use the Git log to check the commit history:

```shell
git log
```

![](docs/readme06.PNG)

Note: use the `q` key to quit from commit history.

### Push (Upload) commits

Now you can push your changes to GitLab by:

```shell
git push origin <branch-name>
```

![](docs/readme07.PNG)

You should be able to view your pushed branch in the GitLab repository now.

![](docs/readme08.PNG)

### Create merge request

Obviously, click the `Create merge request`. In the next page, press the `Create merge request` button again.
You can also review your commits and changes using the tabs below.

![](docs/readme09.PNG)

### Congratulations 🎉

After completing all these steps, you can just sit back, relax, and wait for the administrators to merge your branch.
Meanwhile, you can check if they comment on your merge request through:

![](docs/readme10.PNG)
