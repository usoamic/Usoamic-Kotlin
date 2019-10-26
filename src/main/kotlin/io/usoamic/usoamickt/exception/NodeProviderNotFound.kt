package io.usoamic.usoamickt.exception

import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import java.lang.Exception

class NodeProviderNotFound(provider: NodeProvider, networkType: NetworkType) : Exception("Provider $provider of $networkType not found")