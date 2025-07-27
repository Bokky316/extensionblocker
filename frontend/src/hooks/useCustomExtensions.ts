import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {
  fetchCustomExtensions,
  createCustomExtension,
  removeCustomExtension,
} from '@/store/slices/customExtensionsSlice'
import type { RootState, AppDispatch } from '@/store'

export const useCustomExtensions = () => {
  const dispatch = useDispatch<AppDispatch>()
  const { list, loading, error } = useSelector(
    (state: RootState) => state.customExtensions
  )

  useEffect(() => {
    dispatch(fetchCustomExtensions())
  }, [dispatch])

  const addExtension = (name: string) => {
    dispatch(createCustomExtension(name))
  }

  const deleteExtension = (id: number) => {
    dispatch(removeCustomExtension(id))
  }

  return {
    customList: list,
    loading,
    error,
    addExtension,
    deleteExtension,
  }
}
