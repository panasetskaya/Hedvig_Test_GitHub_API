package com.panasetskaia.hedvigtestgithubapi.presentation


fun screenSlashesInRepoName(repoName: String): String {
    return  repoName.replace("/","-")
}