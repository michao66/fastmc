package cn.fastmc.core;

public class ResolveClass {
	protected static final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

	public static Class resolveClass(String alias) {
		if (alias == null)
			return null;
		try {
			return resolveAlias(alias);
		} catch (Exception e) {
			throw new ServiceException("Error resolving class . Cause: " + e, e);
		}
	}

	public static Object resolveInstance(String alias) {
		if (alias == null)
			return null;
		try {
			Class type = resolveClass(alias);
			return type.newInstance();
		} catch (Exception e) {
			throw new ServiceException("Error instantiating class. Cause: " + e, e);
		}
	}

	public static Object resolveInstance(Class type) {
		if (type == null)
			return null;
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new ServiceException("Error instantiating class. Cause: " + e, e);
		}
	}

	public static Class resolveAlias(String alias) {
		return typeAliasRegistry.resolveAlias(alias);
	}
}
