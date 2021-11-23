package com.thanh_nguyen.baseproject.di

import org.kodein.di.Kodein


/**
 * module for repository dependencies
 */

const val REPO_MODULE = "repo_module"

val repositoryModule = Kodein.Module(REPO_MODULE, false){

}
