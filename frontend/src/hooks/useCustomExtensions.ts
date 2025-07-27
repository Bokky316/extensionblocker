import { useCallback, useEffect } from 'react'
import { useAppDispatch } from '@/hooks/useAppDispatch'
import { useAppSelector } from '@/hooks/useAppSelector'
import type { RootState } from '@/store'
import {
  fetchCustomExtensionsThunk,
  createCustomExtensionThunk,
  removeCustomExtensionThunk,
} from '@/store/slices/customExtensionsSlice'

export const useCustomExtensions = () => {
  const dispatch = useAppDispatch()

  const customList = useAppSelector(
    (state: RootState) => state.customExtensions?.list ?? []
  )
  const loading = useAppSelector(
    (state: RootState) => state.customExtensions?.loading ?? false
  )
  const error = useAppSelector(
    (state: RootState) => state.customExtensions?.error ?? null
  )

  const fetch = useCallback(() => {
    dispatch(fetchCustomExtensionsThunk())
  }, [dispatch])

  const addExtension = useCallback(
    async (name: string) => {
      return await dispatch(createCustomExtensionThunk(name)).unwrap()
    },
    [dispatch]
  )

  const removeExtension = useCallback(
    async (id: number) => {
      return await dispatch(removeCustomExtensionThunk(id)).unwrap()
    },
    [dispatch]
  )

  useEffect(() => {
    fetch()
  }, [fetch])

  return {
    customList,
    loading,
    error,
    addExtension,
    removeExtension,
  }
}
