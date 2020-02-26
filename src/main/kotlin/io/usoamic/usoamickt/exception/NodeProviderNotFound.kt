package io.usoamic.usoamickt.exception

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider

class NodeProviderNotFound(provider: NodeProvider, networkType: NetworkType) : Exception("Provider $provider of $networkType not found")